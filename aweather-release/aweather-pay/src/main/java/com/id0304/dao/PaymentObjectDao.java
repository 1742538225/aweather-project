package com.id0304.dao;

import com.id0304.entity.PaymentObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PaymentObjectDao {
    @Select("select id,name,price,description from payment_object")
    List<PaymentObject> getAllObject();
}
