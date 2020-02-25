package com.id0304.dao;

import com.id0304.entity.CityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CityDao {
    @Select("select provinceZh from city group by provinceZh")
    List<String> getAllProvince();

    @Select("select cityZh from city where provinceZh = #{provinceZh}")
    List<String> getCityByProvince(@Param("provinceZh") String provinceZh);

    @Select("select id from city where cityZh = #{cityZh} and provinceZh = #{provinceZh}")
    String getCityIdByCityZhAndProvinceZh(@Param("cityZh") String cityZh,@Param("provinceZh")String provinceZh);

    @Select("select id,cityZh,provinceZh,countryZh from city where id = #{id}")
    CityEntity getCityById(@Param("id") String id);
}
