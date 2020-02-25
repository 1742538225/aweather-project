package com.id0304.adapter;

import com.id0304.entity.PaymentInfo;
import com.id0304.entity.PaymentType;

public interface PayAdapter {
    String pay(PaymentInfo paymentInfo, PaymentType paymentType);
}
