package com.id0304.service;

import com.id0304.api.BaseApiService;
import com.id0304.dao.PaymentTypeDao;
import com.id0304.entity.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentTypeServiceImpl extends BaseApiService implements PaymentTypeService {
    @Autowired
    private PaymentTypeDao paymentTypeDao;

    @Override
    public Map<String, Object> getPaymentType(@RequestParam("id") Long id) {
        PaymentType paymentType = paymentTypeDao.getPaymentType(id);
        if(paymentType == null){
            return setResultErrorData("未查找到支付类型");
        }
        return setResultSuccessData(paymentType);
    }
}
