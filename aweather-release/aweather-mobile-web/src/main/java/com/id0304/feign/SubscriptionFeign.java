package com.id0304.feign;

import entity.SubscriptionEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
@FeignClient(name = "aweather-user")
@RequestMapping("/user")
public interface SubscriptionFeign {
    /**
     * @Author WuZhengHua
     * @Description TODO 增加一条订阅信息
     * @Date 22:24 2019/8/14
     **/
    @PostMapping("/addSubscription")
    Map<String, Object> addSubscription(@RequestBody SubscriptionEntity subscriptionEntity);

    /**
     * @Author WuZhengHua
     * @Description TODO 删除一条订阅信息
     * @Date 22:25 2019/8/14
     **/
    @GetMapping("/deleteSubscription")
    Map<String, Object> deleteSubscription(@RequestParam("id") Long id);

    /**
     * @Author WuZhengHua
     * @Description TODO 根据用户id查询订阅信息
     * @Date 22:27 2019/8/14
     **/
    @GetMapping("/getSubscriptionByUid")
    Map<String, Object> getSubscriptionByUid(@RequestParam("uId") Long uId);

    /**
     * @Author WuZhengHua
     * @Description TODO 修改一条订阅信息
     * @Date 22:28 2019/8/14
     **/
    @PostMapping("/updateSubscription")
    Map<String, Object> updateSubscription(@RequestBody SubscriptionEntity subscriptionEntity);
}
