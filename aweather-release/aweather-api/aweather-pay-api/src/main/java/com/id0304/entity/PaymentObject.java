package com.id0304.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author WuZhengHua
 * @Description TODO
 * @Date 2019/7/15 19:27
 */
@Setter
@Getter
public class PaymentObject extends BaseEntity {
    private String name;
    private Long price;
    private String description;
}
