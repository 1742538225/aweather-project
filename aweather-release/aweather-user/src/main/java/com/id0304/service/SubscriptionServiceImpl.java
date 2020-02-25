package com.id0304.service;

import api.SubscriptionService;
import com.id0304.api.BaseApiService;
import com.id0304.manager.SubscriptionManager;
import entity.SubscriptionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO 用户订阅城市信息服务层
 * @Date 2019/8/14 22:30
 */
@RestController
@Slf4j
public class SubscriptionServiceImpl extends BaseApiService implements SubscriptionService {
    @Autowired
    private SubscriptionManager subscriptionManager;

    @Override
    public Map<String, Object> addSubscription(SubscriptionEntity subscriptionEntity) {
        if (subscriptionEntity == null) {
            log.error("#####添加订阅信息服务层传入参数subscriptionEntity为null");
            return setResultErrorParam("subscriptionEntity不能为null");
        } else {
            return subscriptionManager.addSubscription(subscriptionEntity);
        }
    }

    @Override
    public Map<String, Object> deleteSubscription(Long id) {
        if (id == null) {
            log.error("#####删除订阅信息服务层传入参数id为null");
            return setResultErrorParam("订阅信息id不能为null");
        } else {
            return subscriptionManager.deleteSubscription(id);
        }
    }

    @Override
    public Map<String, Object> getSubscriptionByUid(Long uId) {
        if (uId == null) {
            log.error("#####根据订阅信息id查询订阅信息服务层传入参数id为null");
            return setResultErrorParam("订阅信息id不能为null");
        } else {
            return subscriptionManager.getSubscriptionByUid(uId);
        }
    }

    @Override
    public Map<String, Object> updateSubscription(SubscriptionEntity subscriptionEntity) {
        if (subscriptionEntity == null) {
            log.error("#####修改订阅信息服务层传入参数subscriptionEntity为null");
            return setResultErrorParam("subscriptionEntity不能为null");
        } else {
            return subscriptionManager.updateSubscription(subscriptionEntity);
        }
    }
}
