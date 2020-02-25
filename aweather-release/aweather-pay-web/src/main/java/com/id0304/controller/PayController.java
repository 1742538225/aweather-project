package com.id0304.controller;

import com.alibaba.fastjson.JSONObject;
import com.id0304.entity.PaymentInfo;
import com.id0304.feign.PaymentInfoFeign;
import com.id0304.service.PayService;
import com.id0304.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PaymentInfoFeign paymentInfoFeign;

    @Autowired
    private PayService payService;

    @GetMapping("/toPay.action")
    public void toPay(String token, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //使用token查找对应的支付信息
        try {
            Map<String,Object> payInfoTokenMap = paymentInfoFeign.getPayInfoToken(token);
            Map<String,Object> resultMap = (Map<String, Object>) ResultUtil.getResultMap(payInfoTokenMap);
            String json = new JSONObject().toJSONString(resultMap);
            PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
            if(paymentInfo == null){
                //没有找到该接口
                out.println("没有找到该接口");
            }
            String html = payService.pay(paymentInfo);
            out.println(html);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("系统错误");
        }finally {
            out.close();
        }
    }
}
