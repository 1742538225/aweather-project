package com.id0304.Entity;

import com.id0304.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class WeatherEntity extends BaseEntity {
    private String day;     //08日08时,有未来报导
    private Date date;      //2019-06-08,无未来报导
    private String week;    //星期六,无未来报导
    private String wea;     //多云转阵雨,有未来报导
    private String wea_img;     //lei,无未来报导
    private Integer air;        //0,无未来报导
    private Integer humidity;       //100,无未来报导
    private String air_level;       //优,无未来报导
    private String air_tips;        //空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！,无未来报导
    private String tem1;    //33℃,无未来报导
    private String tem2;       //27℃,无未来报导
    private String tem;     //28℃,有未来报导
    private String win_speed;   //<3级,有未来报导
    private List<WeatherEntity> hours;

}
