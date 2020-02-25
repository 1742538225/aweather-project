package com.id0304.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.id0304.Entity.WeatherEntity;
import com.id0304.api.BaseApiService;
import com.id0304.constants.CookieConstants;
import com.id0304.feign.CityFeign;
import com.id0304.feign.WeatherFeign;
import com.id0304.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
@Slf4j
public class WeatherController extends BaseApiService {
    @Autowired
    private WeatherFeign weatherFeign;

    @Autowired
    private CityFeign cityFeign;

    @GetMapping("/getWeather.action")
    public String[] getWeatherByCityId(HttpServletRequest request) {
        String cityId = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_CITYID);
        if(cityId==null){   //若cookie无值,默认查询汕尾市天气
            cityId = "101282101";
        }
        log.info("#####调用WeatherController的getWeatherByCityId根据城市id:{} 查询天气信息", cityId);
        try {
            String[] weatherJson = new String[3];
            Map<String, Object> map = weatherFeign.getWeatherByCityId(cityId);
            String weatherAllMessage = (String) map.get("data");
            JSONObject weatherObj = JSONObject.parseObject((String) map.get("data"));
            weatherJson[0] = weatherObj.getString("data");//每天的天气json字符串
            JSONArray jsonArray = JSONArray.parseArray(weatherJson[0]);
            List<WeatherEntity> weatherList = jsonArray.toJavaList(WeatherEntity.class);
            List<WeatherEntity> hours = weatherList.get(0).getHours();
            weatherJson[1] = JSON.toJSONString(hours);        //今天详细时间天气json字符串
            weatherJson[2] = weatherObj.getString("city");//获取地区中文名称;
            return weatherJson;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#####系统出错");
            return null;
        }
    }
}
