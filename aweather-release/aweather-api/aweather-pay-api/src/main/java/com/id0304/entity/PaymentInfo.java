package com.id0304.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PaymentInfo extends BaseEntity{
    private Long userId;
    private Long typeId;
    private String orderId;
    private String platformorderId;
    private Long price;
    private String source;
    private String state;
    private String payMessage;
}
