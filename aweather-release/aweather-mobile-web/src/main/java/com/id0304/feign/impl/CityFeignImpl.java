package com.id0304.feign.impl;

import com.id0304.api.BaseApiService;
import com.id0304.feign.CityFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@ResponseBody
public class CityFeignImpl extends BaseApiService implements CityFeign {
    @Override
    public Map<String, Object> getAllProvince() {
        log.error("#####CityFeign - getAllProvince远程获取所有省份服务超时");
        return setResultErrorData("服务器请求超时...");
    }

    @Override
    public Map<String, Object> getCityByProvince(String provinceZh) {
        log.error("#####CityFeign - getCityByProvince远程根据省名查询城市列表服务超时");
        return setResultErrorData("服务器请求超时...");
    }

    @Override
    public Map<String, Object> getCityIdByCityZhAndProvinceZh(String cityZh, String provinceZh) {
        log.error("#####CityFeign - getCityIdByCityZhAndProvinceZh远程查询城市{}-{}的id服务超时",provinceZh,cityZh);
        return setResultErrorData("服务器请求超时...");
    }

    @Override
    public Map<String, Object> getCityById(String id) {
        log.error("#####CityFeign - getCityById远程根据城市id={}查询具体信息服务超时",id);
        return setResultErrorData("服务器请求超时...");
    }
}
