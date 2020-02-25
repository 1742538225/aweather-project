package com.id0304.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.id0304.api.BaseApiService;
import com.id0304.constants.RedisContants;
import com.id0304.dao.CityDao;
import com.id0304.entity.CityEntity;
import com.id0304.manager.CityManager;
import com.id0304.redis.BaseRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CityManagerImpl extends BaseApiService implements CityManager {
    @Autowired
    private CityDao cityDao;

    @Autowired
    private BaseRedisService baseRedisService;

    /**
     * @Author WuZhengHua
     * @Description TODO 获取所有省份-管理层
     * @Date 13:44 2019/7/14
     **/
    @Override
    public Map<String, Object> getAllProvince() {
        String provinceJson = baseRedisService.get(RedisContants.REDIS_KEY_PROVINCE);
        if(provinceJson!=null){
            log.info("#####从redis查询到了所有省份");
            return setResultSuccessData(provinceJson);
        }else {
            List<String> provinceList = cityDao.getAllProvince();
            provinceJson = JSON.toJSONString(provinceList);
            log.info("#####查询所有省份,向redis中存入省份({}-省份json字符串)",RedisContants.REDIS_KEY_PROVINCE);
            baseRedisService.setString(RedisContants.REDIS_KEY_PROVINCE,provinceJson);
            JSONArray jsonArray = JSONArray.parseArray(provinceJson);
            return setResultSuccessData(jsonArray.toString());
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取指定省份的所有城市-管理层
     * @Date 13:44 2019/7/14
     **/
    @Override
    public Map<String, Object> getCityByProvince(String provinceZh) {
        String cityJson = baseRedisService.get(provinceZh);
        if(cityJson!=null){
            log.info("#####从redis查询到{}的所有城市",provinceZh);
            return setResultSuccessData(cityJson);
        }else {
            List<String> cityList = cityDao.getCityByProvince(provinceZh);
            cityJson =JSON.toJSONString(cityList);
            JSONArray jsonArray = JSONArray.parseArray(cityJson);
            log.info("#####查询{}的所有城市{}", provinceZh, jsonArray);
            log.info("#####将{}的城市json字符串存入redis {}-json字符串 ",provinceZh);
            baseRedisService.setString(provinceZh,cityJson);
            return setResultSuccessData(jsonArray.toString());
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 通过城市中文名称查询城市id,将城市id存入cookie中-管理层
     * @Date 13:45 2019/7/14
     **/
    @Override
    public String getCityIdByCityZhAndProvinceZh(String cityZh,String provinceZh) {
        log.info("#####查询{}省{}的城市id",cityZh);
        String cityId = cityDao.getCityIdByCityZhAndProvinceZh(cityZh,provinceZh);
        if(cityId!=null) {
            log.info("#####查询到id={}",cityId);
            return cityId;
        }else{
            log.info("#####未查询到id");
            return null;
        }
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 根据城市id查询城市-管理层
     * @Date 13:47 2019/7/14
     **/
    @Override
    public Map<String, Object> getCityById(String id) {
        CityEntity cityEntity = cityDao.getCityById(id);
        log.info("#####manager层查询id为{}的城市",id);
        String json = JSON.toJSONString(cityEntity);
        return setResultSuccessData(json);
    }
}
