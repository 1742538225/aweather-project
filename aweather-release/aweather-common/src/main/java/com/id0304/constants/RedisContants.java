package com.id0304.constants;

public interface RedisContants {
    Long REDIS_TIMEOUT_WEATHER_DETAIL = 60*60*3L;   //天气数据在缓存保持3小时
    String REDIS_KEY_PROVINCE = "province";     //省的key值
    Long REDIS_TIMEOUT_USER_30DAY = 60*60*24*7L;   //用户登录数据保存7天
    Long REDIS_TIMEOUT_USER_30M = 60*30L;   //用户登录数据保存30分钟
    Long REDIS_TIMEOUT_USER_10M = 60*10L;   //订单id保存10分钟
}
