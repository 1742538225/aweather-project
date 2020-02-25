package com.id0304.dao;

import com.id0304.mybatis.BaseDao;
import entity.RoleEntity;
import entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface UserDao extends BaseDao {
    @Select("select id,password,email,name,role,vip_start,vip_stop,created from user where email = #{email}")
    UserEntity getUserByEmail(@Param("email") String email);

    @Select("select id,password,email,name,role from user where email = #{email} and password = #{password}")
    UserEntity getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("select id,password,email,name,role,vip_stop from user where id = #{id}")
    UserEntity getUserById(@Param("id") long id);

    @Select("select id,name from role")
    List<RoleEntity> getAllRole();

    @Update("update user set email = #{email},name=#{name} where id = #{id}")
    void updateEmailAndName(UserEntity userEntity);
}
