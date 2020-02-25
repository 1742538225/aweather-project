package com.id0304.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@FeignClient(name = "aweather-pay")
public interface PaymentTypeFeign {
    @GetMapping("/pay/getPaymentType")
    Map<String,Object> getPaymentType(@RequestParam("id") Long id);
}
