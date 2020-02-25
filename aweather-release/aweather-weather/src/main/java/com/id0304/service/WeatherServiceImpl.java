package com.id0304.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.id0304.Entity.WeatherEntity;
import com.id0304.api.BaseApiService;
import com.id0304.api.WeatherService;
import com.id0304.manager.WeatherManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class WeatherServiceImpl extends BaseApiService implements WeatherService{
    @Autowired
    private WeatherManager weatherManager;

    @Override
    public Map<String, Object> getWeatherByCityId(@RequestParam("id") String id) {
        Map<String, Object> map = weatherManager.getWeatherByCityId(id);
        return map;
    }
}
