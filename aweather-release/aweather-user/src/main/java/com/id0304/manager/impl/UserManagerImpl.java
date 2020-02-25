package com.id0304.manager.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.id0304.api.BaseApiService;
import com.id0304.constants.*;
import com.id0304.dao.UserDao;
import com.id0304.entity.PaymentInfo;
import com.id0304.manager.UserManager;
import com.id0304.mq.producer.RegistMailBoxProducer;
import com.id0304.redis.BaseRedisService;
import com.id0304.utils.*;
import com.netflix.client.http.HttpResponse;
import entity.RoleEntity;
import entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserManagerImpl extends BaseApiService implements UserManager {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RegistMailBoxProducer registMailBoxProducer;

    @Autowired
    private BaseRedisService baseRedisService;

    @Override
    public Map<String, Object> regist(UserEntity userEntity) {
        log.info("#####调用UserManagerImpl的regist方法注册用户");
        userEntity.setCreated(DateUtil.getTimestamp());
        userEntity.setUpdated(DateUtil.getTimestamp());
        userEntity.setRole(RoleContants.ROLE_MEMBER);
        userEntity.setPassword(Md5AndBase64SoltUtil.Md5AndBase64Solt(userEntity.getPassword(), userEntity.getEmail()));
        userDao.save(userEntity, TableNameConstants.TABLE_USER);
        log.info("#####注册成功");

        //注册成功,发送邮件
        //队列
        Destination destination = new ActiveMQQueue(MQContants.QUEUE_NAME_MAIL);
        //组装报文格式
        String mailMessage = mailMessage(userEntity.getEmail());
        log.info("#####regist() 注册发送邮件报文mailMessage:{}", mailMessage);
        //进入ActiveMQ
        registMailBoxProducer.send(destination, mailMessage);
        return setResultSuccess();
    }

    //组装自定义报文,根据实际情况自定义

    /**
     * 这里注意,我们是将一个json字符串存进了队列里作为自定义的消息队列报文,格式如下:
     * "root" : {
     * "header" : {
     * "interfaceType" : "接口类型"
     * },
     * "content" : {
     * //消息体内容,即要传什么数据到队列里,这里传了email地址和用户名
     * email : ""
     * }
     * }
     *
     * @param email
     * @return
     */
    @Override
    public String mailMessage(String email) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", MQContants.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        root.put("header", header);
        root.put("content", content);
        return root.toString();
    }

    @Override
    public Boolean ifExistEmail(String email) {
        UserEntity userEntity = userDao.getUserByEmail(email);
        if (userEntity == null) {
            return false;
        }
        return true;
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 登录-管理层,将生成用户token存入cookie,再将token-email存入redis
     * @Date 18:13 2019/7/13
     **/
    @Override
    public Map<String, Object> login(UserEntity userEntity) {
        String email = userEntity.getEmail();
        String password = userEntity.getPassword();
        String en_password = Md5AndBase64SoltUtil.Md5AndBase64Solt(password, email);
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email, en_password);
        //3.执行登录方法
        try {
            subject.login(usernamePasswordToken);//执行后会来到realm的认证逻辑代码
            String token = TokenUtil.getUserToken();
            baseRedisService.setString(token, email, RedisContants.REDIS_TIMEOUT_USER_30M);
            log.info("#####登录成功!用户邮箱:{}", email);
            return setResultSuccessData(token);
        } catch (UnknownAccountException e) {
            //登陆失败:用户名不存在
            log.info("#####登录失败,用户名{}不存在", email);
            return setResultFailData("用户名错误");
        } catch (IncorrectCredentialsException e) {
            //登陆失败:密码错误
            log.info("#####登录失败,密码错误");
            return setResultFailData("密码错误");
        } catch (Exception e) {
            log.info("#####登录过程中服务器错误");
            return setResultError();
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 注销服务-管理层
     * @Date 19:44 2019/8/8
     **/
    @Override
    public Map<String, Object> logout(String token) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            baseRedisService.delete(token);
            log.info("#####注销成功!");
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####注销过程服务器错误");
            return setResultError();
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 根据token获取用户信息-管理层
     * @Date 18:25 2019/7/13
     **/
    @Override
    public Map<String, Object> getUserByToken(String token) {
        String email = baseRedisService.get(token);
        if (email == null) {
            log.info("#####Redis中查不到令牌为{}的用户,用户信息过期", token);
            return setResultFailData("用户登录信息过期");
        }
        UserEntity userEntity = userDao.getUserByEmail(email);

        if (userEntity.getVip_stop()!=null&&DateUtil.calLastedTime(userEntity.getVip_stop(), DateUtil.getTimestamp()) <= 0) {
            log.info("#####查询到用户会员已经过期,取消会员资格");
            userEntity.setVip_stop(null);
            userDao.update(userEntity, TableNameConstants.TABLE_USER);
        }
        userEntity.setPassword(null);
        log.info("#####查找成功!用户邮箱:{}", userEntity.getEmail());
        return setResultSuccessData(userEntity);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 修改用户信息-管理层
     * @Date 19:51 2019/7/13
     **/
    @Override
    public Map<String, Object> updateUser(UserEntity userEntity) {
        String email = userEntity.getEmail();
        String password = userEntity.getPassword();
        userEntity.setUpdated(DateUtil.getTimestamp());
        String en_password = Md5AndBase64SoltUtil.Md5AndBase64Solt(password, email);
        userEntity.setPassword(en_password);
        userDao.update(userEntity, TableNameConstants.TABLE_USER);
        log.info("#####修改成功!用户id:{}", userEntity.getId());
        return setResultSuccess();
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 修改用户信息-管理层
     * @Date 19:51 2019/7/13
     **/
    @Override
    public Map<String,Object> updateEmailAndName(UserEntity userEntity) {
        try {
            userDao.updateEmailAndName(userEntity);
            log.info("#####修改成功!用户id:{}", userEntity.getId());
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####修改失败,服务器异常");
            return setResultError();
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 根据用户邮箱查询用户信息-管理层
     * @Date 12:23 2019/7/14
     **/
    @Override
    public Map<String, Object> getUserByEmail(String email) {
        UserEntity userEntity = userDao.getUserByEmail(email);
        if (userEntity == null) {
            return setResultFailData("未找到该用户!");
        }
        return setResultSuccessData(userEntity);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取所有身份列表-管理层
     * @Date 13:31 2019/7/14
     **/
    @Override
    public Map<String, Object> getAllRole() {
        List<RoleEntity> roleEntityList = userDao.getAllRole();
        if (roleEntityList == null) {
            return setResultFailData("列表中未找到任何身份!");
        }
        String json = JSONUtils.ListToJsonString(roleEntityList);
        return setResultSuccessData(json);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 修改用户vip到期时间,根据订单修改
     * @Date 20:08 2019/8/10
     **/
    @Override
    public Map<String, Object> updateUserVipStop(PaymentInfo paymentInfo) {
        log.info("#####进入修改会员到期时间服务--管理层");
        UserEntity userEntity = userDao.getUserById(paymentInfo.getUserId());
        Long price = paymentInfo.getPrice();
        Long day = 0L;
        if (userEntity.getVip_stop() == null) {
            userEntity.setVip_stop(DateUtil.getTimestamp());
        }
        if (price == 300) {
            day = 7L;
        } else if (price == 1000) {
            day = 30L;
        } else if (price == 5000) {
            day = 180L;
        } else if (price == 10000) {
            day = 365L;
        }
        try {
            Timestamp timestamp = userEntity.getVip_stop();
            log.info("#####取到旧的用户会员到期时间戳:{}", timestamp);
            Date newDate = DateUtil.addDate(DateUtil.TimestampToDate(timestamp), day);
            userEntity.setVip_stop(DateUtil.DateToTimestamp(newDate));
            log.info("#####更新用户会员到期时间戳:{}", userEntity.getVip_stop());
        } catch (ParseException e) {
            log.error("#####时间戳加一天DateUtil.addDate代码出错");
            e.printStackTrace();
        }
        userDao.update(userEntity, TableNameConstants.TABLE_USER);
        log.info("#####成功修改会员到期时间服务--管理层,会员价格:{},会员延期:{}天,到期时间:{}", price / 100, day, userEntity.getVip_stop());
        return setResultSuccess();
    }
}
