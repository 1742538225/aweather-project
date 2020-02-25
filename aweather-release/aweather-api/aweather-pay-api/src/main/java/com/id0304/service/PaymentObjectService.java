package com.id0304.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/pay")
public interface PaymentObjectService {
    @GetMapping("/getAllObject")
    Map<String,Object> getAllObject();
}
