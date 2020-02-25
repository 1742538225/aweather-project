package com.id0304.feign;


import com.id0304.entity.PaymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
@FeignClient(name = "aweather-pay")
public interface PaymentInfoFeign {

    //创建交易信息
    @PostMapping("/pay/addPayInfoToken")
    Map<String,Object> addPayInfoToken(@RequestBody PaymentInfo paymentInfo);

    //使用token获取支付信息
    @GetMapping("/pay/getPayInfoToken")
    Map<String,Object> getPayInfoToken(@RequestParam("token") String token);

    //使用订单号查找支付信息
    @GetMapping("/pay/getByOrderIdPayInfo")
    Map<String,Object> getByOrderIdPayInfo(@RequestParam("orderId") String orderId);

    //修改支付信息
    @PostMapping("/pay/updatePayInfo")
    Map<String ,Object> updatePayInfo(@RequestBody PaymentInfo paymentInfo);
}
