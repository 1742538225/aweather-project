package com.id0304.manager;

import java.util.Map;

public interface WeatherManager {

    Map<String,Object> getWeatherByCityId(String id);
}
