package com.id0304.controller.callback;

import com.id0304.service.CallbackService;
import com.id0304.unionpay.acp.sdk.AcpService;
import com.id0304.unionpay.acp.sdk.LogUtil;
import com.id0304.unionpay.acp.sdk.SDKConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/pay/callback")
@Controller
public class YinLianCallbackController {
    @Autowired
    private CallbackService callbackService;
    private static final String PAY_SUCCESS = "redirect:/html/success.html";
    private static final String PAY_FAIL = "redirect:/html/fail.html";

    @PostMapping("/syn.action")
    public String syn(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
        Map<String, String> reqParam = callbackService.syn(req);
        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        String encoding = req.getParameter(SDKConstants.param_encoding);
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题
            return PAY_FAIL;
        }
        req.setAttribute("txnAmt", Double.parseDouble(reqParam.get("txnAmt")) / 100);
        req.setAttribute("orderId", Long.parseLong(reqParam.get("orderId")));
        LogUtil.writeLog("验证签名结果[成功].");
        return PAY_SUCCESS;
    }

    @PostMapping("/asyn.action")
    public String asyn(HttpServletRequest req) {
        return callbackService.asyn(req);
    }
}
