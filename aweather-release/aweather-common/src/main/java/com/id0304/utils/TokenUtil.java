package com.id0304.utils;

import java.util.UUID;

public class TokenUtil {
    //获取登录token
    public static String getUserToken() {
        return "user-"+UUID.randomUUID().toString();
    }

    //获取支付token
    public static String getPayToken() {
        return "pay-" + UUID.randomUUID().toString();
    }
}