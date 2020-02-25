package com.id0304.service;

import api.UserService;
import com.alibaba.druid.util.StringUtils;
import com.id0304.api.BaseApiService;
import com.id0304.constants.BaseApiConstants;
import com.id0304.entity.PaymentInfo;
import com.id0304.manager.UserManager;
import entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

@RestController
@Slf4j
public class UserServiceImpl extends BaseApiService implements UserService {

    @Autowired
    private UserManager userManager;

    /**
     * @Author WuZhengHua
     * @Description TODO 注册服务-服务层
     * @Date 17:59 2019/7/13
     **/
    @Override
    public Map<String, Object> regist(@RequestBody UserEntity userEntity) {
        if (userEntity.getEmail() == null) {
            return setResultErrorParam("邮箱不能为空!");
        }
        if (userEntity.getPassword() == null) {
            return setResultErrorParam("密码不能为空!");
        }
        if (!userEntity.getEmail().matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
            return setResultErrorParam("邮箱格式错误!");
        }
        Boolean ifExistEmail = userManager.ifExistEmail(userEntity.getEmail());
        if (ifExistEmail) {
            return setResultErrorParam("邮箱已存在!");
        }
        try {
            return userManager.regist(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####注册失败");
            return setResultError();
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 登录服务-服务层
     * @Date 17:58 2019/7/13
     **/
    @Override
    public Map<String, Object> login(UserEntity userEntity) {
        return userManager.login(userEntity);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 注销服务-服务层
     * @Date 19:41 2019/8/8
     **/
    @Override
    public Map<String, Object> logout(String token) {
        return userManager.logout(token);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 根据token获取用户信息-服务层
     * @Date 18:26 2019/7/13
     **/
    @Override
    public Map<String, Object> getUserByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return setResultErrorParam("token不能为空!");
        }
        return userManager.getUserByToken(token);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 修改用户信息-服务层
     * @Date 19:06 2019/7/13
     **/
    @Override
    public Map<String, Object> updateUser(UserEntity userEntity) {
        if (userEntity == null) {
            return setResultErrorParam("userEntity不能为空!");
        }
        return userManager.updateUser(userEntity);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 根据用户邮箱查询用户-服务层
     * @Date 12:22 2019/7/14
     **/
    @Override
    public Map<String, Object> getUserByEmail(String email) {
        if (email == null) {
            return setResultErrorParam("用户邮箱不能为空!");
        }
        return userManager.getUserByEmail(email);
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取所有身份列表
     * @Date 13:30 2019/7/14
     **/
    @Override
    public Map<String, Object> getAllRole() {
        return userManager.getAllRole();
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 完成支付之后修改用户vip到期时间
     * @Date 19:55 2019/8/10
     **/
    @Override
    public Map<String, Object> updateUserVipStop(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            return setResultErrorParam("参数错误");
        } else {
            return userManager.updateUserVipStop(paymentInfo);
        }
    }

    @Override
    public Map<String, Object> updateEmailAndName(UserEntity userEntity) {
        if (userEntity == null) {
            log.error("传入参数有误,修改用户邮箱姓名失败");
            return setResultErrorParam("fail");
        } else {
            Map<String, Object> map = userManager.updateEmailAndName(userEntity);
            if (map.get(BaseApiConstants.HTTP_CODE_NAME).equals(BaseApiConstants.HTTP_200_CODE)) {
                log.info("#####修改用户邮箱姓名成功");
                return setResultSuccess();
            } else {
                log.info("#####修改用户邮箱姓名失败");
                return setResultError();
            }
        }
    }
}
