package com.id0304.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@FeignClient("aweather-weather")
public interface WeatherFeign {
    @GetMapping("/weather/getWeatherByCityId")
    Map<String,Object> getWeatherByCityId(@RequestParam("id") String id);
}
