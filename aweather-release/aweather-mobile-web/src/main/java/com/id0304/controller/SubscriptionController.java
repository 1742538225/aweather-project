package com.id0304.controller;

import com.alibaba.fastjson.JSONObject;
import com.id0304.constants.BaseApiConstants;
import com.id0304.constants.CookieConstants;
import com.id0304.entity.CityEntity;
import com.id0304.feign.CityFeign;
import com.id0304.feign.SubscriptionFeign;
import com.id0304.feign.UserFeign;
import com.id0304.redis.BaseRedisService;
import com.id0304.utils.CookieUtil;
import com.id0304.utils.ResultUtil;
import entity.SubscriptionEntity;
import entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO
 * @Date 2019/8/16 19:49
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class SubscriptionController {
    @Autowired
    private SubscriptionFeign subscriptionFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CityFeign cityFeign;

    @Autowired
    private BaseRedisService baseRedisService;

    @GetMapping("/addSubscription.action")
    public Integer addSubscription(HttpServletRequest request) {
        List<SubscriptionEntity> subscriptionList = getSubscription(request);

        UserEntity userEntity;
        CityEntity cityEntity;
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        try {
            String userToken = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
            Map<String, Object> userMap = userFeign.getUserByToken(userToken);
            Map<String, Object> userResultMap = (Map<String, Object>) ResultUtil.getResultMap(userMap);
            String userJson = new JSONObject().toJSONString(userResultMap);
            userEntity = new JSONObject().parseObject(userJson, UserEntity.class);
            log.info("#####订阅用户id:{}", userEntity.getId());

            String cityId = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_CITYID);
            Map<String, Object> cityMap = cityFeign.getCityById(cityId);
            String cityJson = (String) cityMap.get(BaseApiConstants.HTTP_DATA_NAME);
            cityEntity = new JSONObject().parseObject(cityJson, CityEntity.class);
            log.info("#####订阅城市:{}-{}-{}", cityEntity.getId(),cityEntity.getProvinceZh(), cityEntity.getCityZh());

            subscriptionEntity.setUId(userEntity.getId());
            subscriptionEntity.setProvinceZh(cityEntity.getProvinceZh());
            subscriptionEntity.setCityId(cityEntity.getId() + "");
            subscriptionEntity.setCityZh(cityEntity.getCityZh());
        } catch (Exception e) {
            log.error("#####用户信息读取失败,未登录或者服务器异常");
            return 0;
        }
        if (userEntity.getVip_stop() == null) { //非会员
            if (subscriptionList.size() == 1) {
                return 2;
            }
        } else {  //会员
            if (subscriptionList.size() == 5) {
                return 3;
            } else {
                Boolean flag = true;    //标记是否重复添加
                for (SubscriptionEntity subscription : subscriptionList) {
                    if (subscriptionEntity.getUId().equals(subscription.getUId()) && subscriptionEntity.getCityId().equals(subscription.getCityId())) {
                        log.error("#####已存在城市{}-{}的订阅数据", subscription.getCityId(), subscription.getCityZh());
                        flag = false;
                    }
                }
                if (flag) {   //订阅成功
                    subscriptionFeign.addSubscription(subscriptionEntity);
                    log.info("#####订阅成功!");
                    return 1;
                } else {  //重复订阅
                    log.error("#####重复订阅!");
                    return -1;
                }
            }
        }


        if (userEntity == null) {
            log.error("#####添加订阅信息控制层传入参数subscriptionEntity为null");
            return 0;   //未登录
        } else {
            log.info("#####订阅控制层-添加订阅信息");
            subscriptionFeign.addSubscription(subscriptionEntity);
            return 1;
        }
    }

    @GetMapping("/deleteSubscription.action")
    public Integer deleteSubscription(@RequestParam("id") Long id, HttpServletRequest request) {
        try {
            String userToken = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
            if (userToken == null) {
                log.error("#####删除订阅失败,登录信息过期");
                return -1;
            }
            log.info("#####订阅控制层-删除id为{}的订阅信息", id);
            subscriptionFeign.deleteSubscription(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####删除订阅失败,服务器异常");
            return 0;
        }
    }

    @GetMapping("/getSubscription.action")
    public List<SubscriptionEntity> getSubscription(HttpServletRequest request) {
        UserEntity userEntity;
        try {
            String token = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
            log.info("获取订单信息,用户token:{}",token);
            if(token == null){
                return null;
            }
            Map<String, Object> userMap = userFeign.getUserByToken(token);
            Map<String, Object> resultMap = (Map<String, Object>) ResultUtil.getResultMap(userMap);
            String json = new JSONObject().toJSONString(resultMap);
            userEntity = new JSONObject().parseObject(json, UserEntity.class);
        } catch (Exception e) {
            log.error("#####用户信息读取失败,未登录或者服务器异常");
            e.printStackTrace();
            return null;
        }
        if (userEntity == null) {
            log.error("#####根据订阅信息id查询订阅信息控制层传入参数id为null");
            return null;
        } else {
            Long uId = userEntity.getId();
            log.info("#####订阅控制层-查询用户id为{}的所有订阅信息", uId);
            Map<String, Object> resultMap = subscriptionFeign.getSubscriptionByUid(uId);
            String json = (String) resultMap.get(BaseApiConstants.HTTP_DATA_NAME);
            List<SubscriptionEntity> subscriptionList = JSONObject.parseArray(json, SubscriptionEntity.class);
            return subscriptionList;
        }
    }

    @PostMapping("/updateSubscription.action")
    public Integer updateSubscription(@RequestBody SubscriptionEntity subscriptionEntity) {
        if (subscriptionEntity == null) {
            log.error("#####修改订阅信息控制层传入参数subscriptionEntity为null");
            return 0;
        } else {
            Long id = subscriptionEntity.getId();
            log.info("#####订阅控制层-修改id为{}的订阅信息", id);
            subscriptionFeign.updateSubscription(subscriptionEntity);
            return 1;
        }
    }
}
