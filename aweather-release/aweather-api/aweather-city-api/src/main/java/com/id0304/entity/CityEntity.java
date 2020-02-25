package com.id0304.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityEntity extends BaseEntity {
    private String cityEn;
    private String cityZh;
    private String provinceEn;
    private String provinceZh;
    private String contryEn;
    private String contryZh;
    private String leaderEn;
    private String leaderZh;
    private String lat;
    private String lon;
}
