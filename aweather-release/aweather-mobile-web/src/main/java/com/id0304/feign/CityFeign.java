package com.id0304.feign;

import com.id0304.feign.impl.CityFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service    //注入spring容器
@FeignClient(value = "aweather-city",fallback = CityFeignImpl.class)	//这里写服务名称,即spring-application-name配置的属性名称
public interface CityFeign {
    @GetMapping("/city/getAllProvince")
    Map<String, Object> getAllProvince();

    @GetMapping("/city/getCityByProvince")
    Map<String,Object> getCityByProvince(@RequestParam("provinceZh") String provinceZh);

    @GetMapping("/city/getCityIdByCityZhAndProvinceZh")
    Map<String,Object> getCityIdByCityZhAndProvinceZh(@RequestParam("cityZh") String cityZh,@RequestParam("provinceZh") String provinceZh);

    @GetMapping("/city/getCityById")
    Map<String ,Object> getCityById(@RequestParam("id") String id);
}
