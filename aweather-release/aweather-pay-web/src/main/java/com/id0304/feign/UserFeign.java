package com.id0304.feign;

import com.id0304.entity.PaymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@FeignClient(name = "aweather-user")
public interface UserFeign {
    /**
     * @Author WuZhengHua
     * @Description TODO 完成支付之后修改用户vip到期时间
     * @Date 19:55 2019/8/10
     **/
    @PostMapping("/user/updateUserVipStop")
    Map<String, Object> updateUserVipStop(@RequestBody PaymentInfo paymentInfo);
}
