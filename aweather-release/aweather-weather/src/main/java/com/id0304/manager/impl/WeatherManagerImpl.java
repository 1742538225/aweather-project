package com.id0304.manager.impl;

import com.id0304.api.BaseApiService;
import com.id0304.constants.RedisContants;
import com.id0304.dao.WeatherDao;
import com.id0304.manager.WeatherManager;
import com.id0304.redis.BaseRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class WeatherManagerImpl extends BaseApiService implements WeatherManager {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private BaseRedisService baseRedisService;

    @Override
    public Map<String, Object> getWeatherByCityId(String id) {
        log.info("#####调用WeatherServiceImpl的getWeatherByCityId服务根据城市id:{} 查询天气", id);
        try {
            if (id != null) {
                if (baseRedisService.get(id) != null) {
                    log.info("#####从redis获取id为{}的城市天气信息", id);
                    return setResultSuccessData(baseRedisService.get(id));
                } else {
                    String weatherUrl = "https://www.tianqiapi.com/api?cityid=" + id;
                    String result = restTemplate.getForObject(weatherUrl, String.class);
                    if(!id.equals("??????")) {
                        log.info("#####将城市id为{}的城市天气数据存入redis(id-天气信息json字符串)", id);
                        baseRedisService.setString(id, result, RedisContants.REDIS_TIMEOUT_WEATHER_DETAIL);
                        return setResultSuccessData(result);
                    }else{
                        log.info("#####获取当前城市的天气json字符串", id);
                        return setResultSuccessData(result);
                    }
                }
            } else {
                log.error("#####参数城市id不能为空");
                return setResultErrorParam("参数错误");
            }
        } catch (Exception e) {
            log.error("#####服务器出错");
            e.printStackTrace();
            return setResultError();
        }

    }
}
