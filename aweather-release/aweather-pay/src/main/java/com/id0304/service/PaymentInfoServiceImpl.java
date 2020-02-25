package com.id0304.service;

import com.alibaba.druid.util.StringUtils;
import com.id0304.api.BaseApiService;
import com.id0304.constants.RedisContants;
import com.id0304.dao.PaymentInfoDao;
import com.id0304.entity.PaymentInfo;
import com.id0304.redis.BaseRedisService;
import com.id0304.utils.DateUtil;
import com.id0304.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PaymentInfoServiceImpl extends BaseApiService implements PaymentInfoService {

    @Autowired
    private PaymentInfoDao paymentInfoDao;

    @Autowired
    private BaseRedisService baseRedisService;

    @Override
    public Map<String, Object> addPayInfoToken(@RequestBody PaymentInfo paymentInfo) {
        //添加paymentInfo
        paymentInfo.setCreated(DateUtil.getTimestamp());
        paymentInfo.setUpdated(DateUtil.getTimestamp());
        paymentInfoDao.savePaymentInfo(paymentInfo);
        Long id = paymentInfo.getId();
        if (id == null) {
            return setResultErrorData("系统错误！");
        }
        //生成对应token
        String payToken = TokenUtil.getPayToken();
        //存放再redis中 key-value：token-id
        baseRedisService.setString(payToken, id + "", RedisContants.REDIS_TIMEOUT_USER_10M);
        return setResultSuccessData(payToken);
    }

    @Override
    public Map<String, Object> getPayInfoToken(@RequestParam("token") String token) {
        //判断token是否为空
        if (StringUtils.isEmpty(token)) {
            return setResultErrorParam("token不能为空");
        }
        //使用token去redis去查找对应的支付id
        String id = baseRedisService.get(token);
        if (StringUtils.isEmpty(id)) {
            return setResultErrorData("该支付信息已过期");
        }
        ;
        //使用支付id查找数据库
        Long newId = Long.parseLong(id);
        PaymentInfo paymentInfo = paymentInfoDao.getPaymentInfo(newId);
        return setResultSuccessData(paymentInfo);
    }

    @Override
    public Map<String, Object> getByOrderIdPayInfo(@RequestParam("orderId") String orderId) {
        PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(orderId);
        if (paymentInfo == null) {
            return setResultErrorData("没有查找到支付信息");
        }
        return setResultSuccessData(paymentInfo);
    }

    @Override
    public Map<String, Object> updatePayInfo(@RequestBody PaymentInfo paymentInfo) {
        paymentInfo.setUpdated(DateUtil.getTimestamp());
        paymentInfoDao.updatePayInfo(paymentInfo);
        return setResultSuccess();
    }
}
