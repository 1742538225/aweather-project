package com.id0304.dao;

import com.id0304.mybatis.BaseDao;
import entity.SubscriptionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SubscriptionDao extends BaseDao{

    @Delete("delete from subscription where id = #{id}")
    void deleteSubscription(Long id);

    @Select("select id,uid,provinceZh,cityid,cityZh,ifemail from subscription where uid = #{id}")
    List<SubscriptionEntity> getSubscriptionByUid(Long id);
}
