package com.id0304.controller;

import com.alibaba.fastjson.JSONObject;
import com.id0304.constants.BaseApiConstants;
import com.id0304.constants.CookieConstants;
import com.id0304.feign.UserFeign;
import com.id0304.utils.CookieUtil;
import com.id0304.utils.ResultUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import entity.UserEntity;
import feign.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserFeign userFeign;

    @PostMapping("/doRegist.action")
    public Integer doRegist(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        log.info("#####调用UserController的doRegist服务进行注册,注册邮箱:{}", userEntity.getEmail());
        try {
            Map<String, Object> map = userFeign.regist(userEntity);
            Integer code = (Integer) map.get(BaseApiConstants.HTTP_CODE_NAME);
            String msg = (String) map.get(BaseApiConstants.HTTP_MESSAGE_NAME);
            String data = (String) map.get(BaseApiConstants.HTTP_DATA_NAME);
            if (code == 200) {
                log.info("#####注册成功");
                return 1;
            } else {
                log.info("#####注册失败,错误信息:{}", data);
                if (data.equals("邮箱不能为空!")) {
                    return 2;
                }
                if (data.equals("密码不能为空!")) {
                    return 3;
                }
                if (data.equals("邮箱格式错误!")) {
                    return 4;
                }
                if (data.equals("邮箱已存在!")) {
                    return 5;
                }
                return 6;
            }
        } catch (Exception e) {
            log.info("#####注册失败,服务器错误");
            e.printStackTrace();
            return 6;
        }
    }

    @RequestMapping("/doLogin.action")
    public Integer doLogin(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        if (userEntity == null) {
            return -1;   //参数错误
        }
        try {
            Map<String, Object> map = userFeign.login(userEntity);
            Integer code = (Integer) map.get(BaseApiConstants.HTTP_CODE_NAME);
            String data = (String) map.get(BaseApiConstants.HTTP_DATA_NAME);
            if (code.equals(BaseApiConstants.HTTP_200_CODE)) {
                //登录成功
                CookieUtil.addCookie(response, "userToken", data, CookieConstants.COOKIE_TIMEOUT_30M);
                log.info("#####登陆成功,token={}", data);
                return 1;
            } else if (code.equals(BaseApiConstants.HTTP_202_CODE)) {
                //登录信息错误
                if (data.equals("密码错误")) {
                    log.info("#####登陆失败,密码错误");
                    return 2;
                } else if (data.equals("用户名错误")) {
                    log.info("#####登陆失败,用户名错误");
                    return 3;
                }
            }
            return 0;
        } catch (Exception e) {
            log.error("#####登陆失败,服务器错误");
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 注销服务-控制层
     * @Date 19:50 2019/8/8
     **/
    @GetMapping("/logout.action")
    public Integer logout(HttpServletRequest request) {
        String userToken = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
        Map<String, Object> map = userFeign.logout(userToken);
        Integer code = (Integer) map.get(BaseApiConstants.HTTP_CODE_NAME);
        if (BaseApiConstants.HTTP_200_CODE.equals(code)) {
            log.info("#####注销成功,控制层");
            return 1;
        } else {
            log.error("#####注销失败,控制层");
            return 0;
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 修改用户信息
     * @Date 18:44 2019/8/17
     **/
    @PostMapping("/updateEmailAndName.action")
    public Integer updateEmailAndName(@RequestBody UserEntity userEntity, HttpServletRequest request) {
        try {
            String userToken = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
            if (userToken == null) {
                log.error("#####用户登录信息过期,修改用户信息失败");
                return -1;//未登录
            } else {
                if (!userEntity.getEmail().matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {
                    log.error("#####邮箱格式错误,修改用户信息失败");
                    return 0;
                }
                userFeign.updateEmailAndName(userEntity);
                log.info("#####修改用户信息成功");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####服务器异常,修改用户信息失败");
            return 2;
        }
    }


    /**
     * @Author WuZhengHua
     * @Description TODO 生成qq授权
     * @Date 18:00 2019/7/18
     **/
    @RequestMapping("/authorizeUrl.action")
    public String authorizeUrl(HttpServletRequest request) throws QQConnectException {
        String authorizeUrl = new Oauth().getAuthorizeURL(request);
        return "redirect:" + authorizeUrl;
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 用户个人中心读取显示用户个人信息
     * @Date 19:43 2019/7/18
     **/
    @GetMapping("/getUserMessage.action")
    public @ResponseBody
    UserEntity getUserMessage(HttpServletRequest request) {
        try {
            String token = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
            log.info("取到用户token:{}",token);
            if (token == null) {
                return null;
            }
            Map<String, Object> userMap = userFeign.getUserByToken(token);
            Integer code = (Integer) userMap.get(BaseApiConstants.HTTP_CODE_NAME);
            if (code.equals(BaseApiConstants.HTTP_202_CODE)) {
                return null;
            }
            Map<String, Object> resultMap = (Map<String, Object>) ResultUtil.getResultMap(userMap);
            String json = new JSONObject().toJSONString(resultMap);
            UserEntity userEntity = new JSONObject().parseObject(json, UserEntity.class);
            return userEntity;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####获取用户信息,服务器错误");
            return null;
        }
    }
}
