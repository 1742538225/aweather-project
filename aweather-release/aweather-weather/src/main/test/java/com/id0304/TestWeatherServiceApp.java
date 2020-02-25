package com.id0304;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.id0304.Entity.WeatherEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestWeatherServiceApp {
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void demo() {
        String weatherUrl = "https://www.tianqiapi.com/api?cityid=101010100";
        String result = restTemplate.getForObject(weatherUrl, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String weatherJson = jsonObject.getString("data");      //每天的天气json字符串
        JSONArray jsonArray = JSONArray.parseArray(weatherJson);
        List<WeatherEntity> weatherList = jsonArray.toJavaList(WeatherEntity.class);
        List<WeatherEntity> hours = weatherList.get(0).getHours();
        String hoursJson = JSON.toJSONString(hours);        //今天详细时间天气json字符串
        log.info(weatherJson);
        log.info(hoursJson);
    }

}
