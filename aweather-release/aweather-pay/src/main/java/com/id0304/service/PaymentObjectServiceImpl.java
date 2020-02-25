package com.id0304.service;

import com.id0304.api.BaseApiService;
import com.id0304.dao.PaymentObjectDao;
import com.id0304.entity.PaymentObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO
 * @Date 2019/7/15 19:45
 */
@RestController
public class PaymentObjectServiceImpl extends BaseApiService implements PaymentObjectService {
    @Autowired
    private PaymentObjectDao paymentObjectDao;

    @Override
    public Map<String, Object> getAllObject() {
        List<PaymentObject> objectList = paymentObjectDao.getAllObject();
        return setResultSuccessData(objectList);
    }
}
