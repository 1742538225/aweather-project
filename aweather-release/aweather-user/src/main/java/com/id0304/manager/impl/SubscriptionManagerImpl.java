package com.id0304.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.id0304.api.BaseApiService;
import com.id0304.constants.TableNameConstants;
import com.id0304.dao.SubscriptionDao;
import com.id0304.manager.SubscriptionManager;
import com.id0304.utils.DateUtil;
import entity.SubscriptionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO 用户订阅管理层实现类
 * @Date 2019/8/14 22:34
 */
@Service
@Slf4j
public class SubscriptionManagerImpl extends BaseApiService implements SubscriptionManager {
    @Autowired
    private SubscriptionDao subscriptionDao;

    @Override
    public Map<String, Object> addSubscription(SubscriptionEntity subscriptionEntity) {
        log.info("#####管理层-添加一条订阅信息");
        try {
            subscriptionEntity.setIfEmail(0);
            subscriptionEntity.setCreated(DateUtil.getTimestamp());
            subscriptionEntity.setUpdated(DateUtil.getTimestamp());
            subscriptionDao.save(subscriptionEntity, TableNameConstants.TABLE_SUBSCRIPTION);
            log.info("#####管理层-添加一条订阅信息成功!");
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("#####管理层-添加一条订阅信息失败!");
            return setResultError();
        }
    }

    @Override
    public Map<String, Object> deleteSubscription(Long id) {
        log.info("#####管理层-删除一条订阅信息");
        try {
            subscriptionDao.deleteSubscription(id);
            log.info("#####管理层-删除一条订阅信息成功!");
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("#####管理层-删除一条订阅信息失败!");
            return setResultError();
        }
    }

    @Override
    public Map<String, Object> getSubscriptionByUid(Long id) {
        log.info("#####管理层-根据用户id{}查询订阅信息",id);
        try {
            List<SubscriptionEntity> subscriptionList = subscriptionDao.getSubscriptionByUid(id);
            String json = JSON.toJSONString(subscriptionList);
            log.info("#####管理层-根据用户id{}查询订阅信息成功!",id);
            return setResultSuccessData(json);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("#####管理层-根据用户id{}查询订阅信息失败!",id);
            return setResultError();
        }
    }

    @Override
    public Map<String, Object> updateSubscription(SubscriptionEntity subscriptionEntity) {
        log.info("#####管理层-修改一条订阅信息");
        try {
            subscriptionEntity.setUpdated(DateUtil.getTimestamp());
            subscriptionDao.update(subscriptionEntity, TableNameConstants.TABLE_SUBSCRIPTION);
            log.info("#####管理层-修改一条订阅信息成功!");
            return setResultSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("#####管理层-修改一条订阅信息失败!");
            return setResultError();
        }
    }
}
