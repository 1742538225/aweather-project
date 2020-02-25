package com.id0304.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("/city")
public interface CityService {
    @GetMapping("/getAllProvince")
    Map<String, Object> getAllProvince();

    @GetMapping("/getCityByProvince")
    Map<String,Object> getCityByProvince(@RequestParam("provinceZh") String provinceZh);

    @GetMapping("/getCityIdByCityZhAndProvinceZh")
    Map<String,Object> getCityIdByCityZhAndProvinceZh(@RequestParam("cityZh") String cityZh,@RequestParam("provinceZh") String provinceZh);

    @GetMapping("/getCityById")
    Map<String ,Object> getCityById(@RequestParam("id") String id);
}
