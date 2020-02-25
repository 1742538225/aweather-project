package com.id0304.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 添加redis信息
     * @param key
     */
    public void setString(String key, String value) {
        set(key, value, null);
    }

    public void setString(String key, String value, Long timeOut) {
        set(key, value, timeOut);
    }

    private void set(String key, Object value, Long timeOut) {
        if (value != null) {
            if (value instanceof String) {
                String setValue = (String) value;
                stringRedisTemplate.opsForValue().set(key, setValue);
            }
            //设置有效期
            if (timeOut != null) {
                stringRedisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 使用key查找redis信息
     * @param key
     */
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 使用key删除redis信息
     * @param key
     */
    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

}