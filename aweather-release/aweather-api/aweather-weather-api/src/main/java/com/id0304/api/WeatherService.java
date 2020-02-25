package com.id0304.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/weather")
public interface WeatherService {
    @GetMapping("/getWeatherByCityId")
    Map<String,Object> getWeatherByCityId(@RequestParam("id") String id);
}
