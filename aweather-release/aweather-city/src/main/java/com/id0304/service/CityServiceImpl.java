package com.id0304.service;

import com.id0304.api.BaseApiService;
import com.id0304.api.CityService;
import com.id0304.manager.CityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
public class CityServiceImpl extends BaseApiService implements CityService {
    @Autowired
    private CityManager cityManager;

    @Override
    public Map<String,Object> getAllProvince() {
        log.info("#####调用CityServiceImpl的getAllProvince查询所有省份服务");
        try {
            return cityManager.getAllProvince();
        } catch (Exception e) {
            log.error("#####服务器出错");
            e.printStackTrace();
            return setResultError();
        }
    }

    @Override
    public Map<String, Object> getCityByProvince(@RequestParam("provinceZh") String provinceZh) {
        log.info("#####调用CityServiceImpl的getCityByProvince查询{}的所有城市",provinceZh);
        try {
            return cityManager.getCityByProvince(provinceZh);
        } catch (Exception e) {
            log.error("#####服务器出错");
            e.printStackTrace();
            return setResultError();
        }
    }

    @Override
    public Map<String, Object> getCityIdByCityZhAndProvinceZh(String cityZh,String ProvinceZh) {
        try {
            String cityId = cityManager.getCityIdByCityZhAndProvinceZh(cityZh,ProvinceZh);
            log.info("#####调用CityServiceImpl的getCityIdByCityZh查询到{}的城市id为{}",cityZh,cityId);
            return setResultSuccessData(cityId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("服务器出错");
            return setResultError();
        }
    }

    @Override
    public Map<String, Object> getCityById(@RequestParam("id") String id) {
        return cityManager.getCityById(id);
    }
}
