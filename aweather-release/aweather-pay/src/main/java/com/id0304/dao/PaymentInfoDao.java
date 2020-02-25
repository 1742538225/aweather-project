package com.id0304.dao;

import com.id0304.entity.PaymentInfo;
import com.id0304.mybatis.BaseDao;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PaymentInfoDao extends BaseDao {

    @Select("select * from payment_info where id = #{id}")
    PaymentInfo getPaymentInfo(@Param("id") Long id);

    @Insert("insert into payment_info (id,userid,typeid,orderid,platformorderid,price,source,state,created,updated) value(null,#{userId},#{typeId},#{orderId},#{platformorderId},#{price},#{source},#{state},#{created},#{updated})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer savePaymentInfo(PaymentInfo paymentInfo);

    @Select("select * from payment_info where orderId=#{orderId}")
    PaymentInfo getByOrderIdPayInfo(@Param("orderId") String orderId);

    @Update("update payment_info set state = #{state},payMessage=#{payMessage},platformorderId=#{platformorderId},updated=#{updated} where orderId=#{orderId}")
    void updatePayInfo(PaymentInfo paymentInfo);
}
