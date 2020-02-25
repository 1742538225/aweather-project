package com.id0304.manager;

import com.id0304.entity.PaymentInfo;
import entity.UserEntity;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

public interface UserManager {
    Map<String, Object> regist(UserEntity userEntity);

    String mailMessage(String email);

    Boolean ifExistEmail(String email);

    Map<String, Object> login(UserEntity userEntity);

    Map<String, Object> getUserByToken(String token);

    Map<String, Object> updateUser(UserEntity userEntity);

    Map<String, Object> getUserByEmail(String email);

    Map<String, Object> getAllRole();

    Map<String,Object> logout(String token);

    Map<String,Object> updateUserVipStop(PaymentInfo paymentInfo);

    Map<String,Object> updateEmailAndName(UserEntity userEntity);
}
