package com.id0304.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/pay")
public interface PaymentTypeService {
    @GetMapping("/getPaymentType")
    Map<String,Object> getPaymentType(@RequestParam("id") Long id);
}
