package com.id0304.manager;

import entity.SubscriptionEntity;

import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO 用户订阅信息管理层接口
 * @Date 22:36 2019/8/14
 **/
public interface SubscriptionManager {
    /**
     * @Author WuZhengHua
     * @Description TODO 增加一条订阅信息
     * @Date 22:24 2019/8/14
     **/
    Map<String, Object> addSubscription(SubscriptionEntity subscriptionEntity);

    /**
     * @Author WuZhengHua
     * @Description TODO 删除一条订阅信息
     * @Date 22:25 2019/8/14
     **/
    Map<String, Object> deleteSubscription(Long id);

    /**
     * @Author WuZhengHua
     * @Description TODO 根据用户id查询订阅信息
     * @Date 22:27 2019/8/14
     **/
    Map<String, Object> getSubscriptionByUid(Long id);

    /**
     * @Author WuZhengHua
     * @Description TODO 修改一条订阅信息
     * @Date 22:28 2019/8/14
     **/
    Map<String, Object> updateSubscription(SubscriptionEntity subscriptionEntity);
}
