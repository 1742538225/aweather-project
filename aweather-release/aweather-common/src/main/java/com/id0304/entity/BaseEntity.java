package com.id0304.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * 封装一些相同的属性和字段
 */
@Setter
@Getter
public class BaseEntity {
    //主键
    private Long id;
    //创建时间
    private Timestamp created;
    //修改时间
    private Timestamp updated;

}
