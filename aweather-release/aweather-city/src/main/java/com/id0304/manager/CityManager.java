package com.id0304.manager;

import java.util.Map;

public interface CityManager {
    Map<String,Object> getAllProvince();

    Map<String,Object> getCityByProvince(String provinceZh);

    String getCityIdByCityZhAndProvinceZh(String cityZh,String ProvinceZh);

    Map<String,Object> getCityById(String id);
}
