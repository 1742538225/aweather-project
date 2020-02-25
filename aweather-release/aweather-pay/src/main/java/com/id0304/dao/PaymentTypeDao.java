package com.id0304.dao;

import com.id0304.entity.PaymentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PaymentTypeDao {
    @Select("select * from payment_type where id = #{id}")
    PaymentType getPaymentType(@Param("id") Long id);
}
