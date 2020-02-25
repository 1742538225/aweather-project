package com.id0304.controller;

import com.alibaba.fastjson.JSONObject;
import com.id0304.constants.BaseApiConstants;
import com.id0304.constants.CookieConstants;
import com.id0304.feign.CityFeign;
import com.id0304.utils.CookieUtil;
import feign.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/city")
@Slf4j
public class CityController {
    @Autowired
    private CityFeign cityFeign;

    @GetMapping("/getAllProvince.action")
    public List<String> getAllProvince() {
        try {
            Map<String, Object> map = cityFeign.getAllProvince();
            String json = (String) map.get("data");
            List<String> provinceList = JSONObject.parseArray(json, String.class);
            log.info("#####调用CityController控制层getAllProvince服务获取所有省份");
            return provinceList;
        } catch (Exception e) {
            log.error("#####调用CityController控制层getAllProvince服务获取所有省份失败");
            return null;
        }
    }

    @GetMapping("/getCityByProvince.action")
    public List<String> getCityByProvince(@RequestParam String province) {
        try {
            if(province.equals("-选择省份-")){
                return null;
            }
            Map<String, Object> map = cityFeign.getCityByProvince(province);
            String json = (String) map.get("data");
            System.err.println(json);
            List<String> cityList = JSONObject.parseArray(json, String.class);
            log.info("#####调用CityController控制层getCityByProvince服务获取{}的所有城市", province);
            return cityList;
        } catch (Exception e) {
            System.err.println(e);
            log.error("#####调用CityController控制层getCityByProvince服务获取{}的所有城市失败", province);
            return null;
        }
    }

    @GetMapping("/setCityIdToCookie.action")
    public String setCityIdToCookie(@RequestParam("provinceZh") String provinceZh,@RequestParam("cityZh") String cityZh, HttpServletResponse response) {
        String cityId = (String) cityFeign.getCityIdByCityZhAndProvinceZh(cityZh,provinceZh).get(BaseApiConstants.HTTP_DATA_NAME);
        log.info("#####向cookie存入城市{}的id={}", cityZh, cityId);
        CookieUtil.removeCookie(response, CookieConstants.COOKIE_NAME_CITYID);
        CookieUtil.addCookie(response, CookieConstants.COOKIE_NAME_CITYID, cityId, CookieConstants.COOKIE_TIMEOUT_15S);
        return BaseApiConstants.HTTP_SUCCESS_NAME;
    }

    @GetMapping("/getCityIdFromCookie.action")
    public String getCityIdFromCookie(HttpServletRequest request,HttpServletResponse response) {
        StringBuffer requestURL = request.getRequestURL();
        try {
            if (request == null) {
                log.info("#####首次加载页面,查询当前城市");
                return "选取当前城市";
            }
            String cityId = (String) CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_CITYID);
            String userToken = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
            log.info("#####从cookie中获取到城市id={}", cityId);
            if (cityId == null || cityId.equals("") || cityId.equals("-选择城市-")) {
                return "选取当前城市";
            } else {
                return (String) CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_CITYID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("#####无法从cookie中获取到城市id,以选取当前城市");
            return "选取当前城市";
        }
    }

    @GetMapping("/removeCityIdFromCookie.action")
    public String removeCityIdFromCookie(HttpServletResponse response) {
        log.info("#####从cookie中移除城市id");
        CookieUtil.removeCookie(response, CookieConstants.COOKIE_NAME_CITYID);
        return BaseApiConstants.HTTP_SUCCESS_NAME;
    }
}
