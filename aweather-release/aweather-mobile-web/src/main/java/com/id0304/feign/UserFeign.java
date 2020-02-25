package com.id0304.feign;

import entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
@FeignClient(name = "aweather-user")
@RequestMapping("/user")
public interface UserFeign {
    @PostMapping("/regist")
    Map<String, Object> regist(@RequestBody UserEntity userEntity);

    @PostMapping("/login")
    Map<String, Object> login(@RequestBody UserEntity userEntity);

    @GetMapping("/getUserByToken")
    Map<String, Object> getUserByToken(@RequestParam("token") String token);

    @PostMapping("/updateUser")
    Map<String, Object> updateUser(@RequestBody UserEntity userEntity);

    @GetMapping("/getUserByEmail")
    Map<String, Object> getUserByEmail(@RequestParam("email") String email);

    @GetMapping("/getAllRole")
    Map<String, Object> getAllRole();

    @GetMapping("/logout")
    Map<String,Object> logout(@RequestParam("token") String token);

    @PostMapping("/updateEmailAndName")
    Map<String,Object> updateEmailAndName(@RequestBody UserEntity userEntity);
}
