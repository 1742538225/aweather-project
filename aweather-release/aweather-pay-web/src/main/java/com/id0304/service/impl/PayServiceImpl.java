package com.id0304.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.id0304.adapter.PayAdapter;
import com.id0304.entity.PaymentInfo;
import com.id0304.entity.PaymentType;
import com.id0304.feign.PaymentTypeFeign;
import com.id0304.service.PayService;
import com.id0304.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private PaymentTypeFeign paymentTypeFeign;

    @Autowired
    private YinLianPayServiceImpl yinLianPayService;

    @Override
    public String pay(PaymentInfo paymentInfo) {
        //判断支付类型
        Long typeId = paymentInfo.getTypeId();
        Map<String,Object> getPaymentTypeMap= paymentTypeFeign.getPaymentType(typeId);
        Map<String,Object>  resultMap = (Map<String, Object>) ResultUtil.getResultMap(getPaymentTypeMap);
        String json = new JSONObject().toJSONString(resultMap);
        PaymentType paymentType = new JSONObject().parseObject(json,PaymentType.class);
        if(paymentType == null){
            //没有找到该接口类型
            return null;
        }
        String typeName = paymentType.getTypename();
        PayAdapter payAdapter = null;
        switch (typeName){
            case "yinlianPay":
                payAdapter = yinLianPayService;
                break;
            default:
                break;
        }
        return payAdapter.pay(paymentInfo,paymentType);
    }
}
