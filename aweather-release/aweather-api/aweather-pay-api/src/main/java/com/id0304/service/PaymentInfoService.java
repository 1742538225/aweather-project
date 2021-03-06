package com.id0304.service;

import com.id0304.entity.PaymentInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 提供支付信息接口服务
 */
@RequestMapping("/pay")
public interface PaymentInfoService {

    //创建交易信息
    @PostMapping("/addPayInfoToken")
    Map<String,Object> addPayInfoToken(@RequestBody PaymentInfo paymentInfo);

    //使用token获取支付信息
    @GetMapping("/getPayInfoToken")
    Map<String,Object> getPayInfoToken(@RequestParam("token") String token);

    //使用订单号查找支付信息
    @GetMapping("/getByOrderIdPayInfo")
    Map<String,Object> getByOrderIdPayInfo(@RequestParam("orderId") String orderId);

    //修改支付信息
    @PostMapping("/updatePayInfo")
    Map<String ,Object> updatePayInfo(@RequestBody PaymentInfo paymentInfo);
}
