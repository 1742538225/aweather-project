package com.id0304.controller;

import com.alibaba.fastjson.JSONObject;
import com.id0304.constants.BaseApiConstants;
import com.id0304.constants.CookieConstants;
import com.id0304.entity.PaymentInfo;
import com.id0304.entity.PaymentObject;
import com.id0304.feign.PayFeign;
import com.id0304.feign.UserFeign;
import com.id0304.utils.CookieUtil;
import com.id0304.utils.DateUtil;
import com.id0304.utils.ResultUtil;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author WuZhengHua
 * @Description TODO 支付服务控制层
 * @Date 2019/7/15 18:00
 */
@RequestMapping("/pay")
@Controller
public class PayController {
    @Autowired
    private PayFeign payFeign;

    @Autowired
    private UserFeign userFeign;

    /**
     * @Author WuZhengHua
     * @Description TODO 根据价格完成添加订单返回订单token
     * @Date 19:25 2019/7/15
     **/
    @PostMapping("/createVipOrder.action")
    public @ResponseBody
    String createVipOrder(HttpServletRequest request, @RequestBody String price) {
        price = price.substring(0, price.indexOf("="));
        String userToken = CookieUtil.getUid(request, CookieConstants.COOKIE_NAME_USERTOKEN);
        if(userToken==null){ //用户登录信息过期
            return "202";
        }
        Map<String, Object> userMap = userFeign.getUserByToken(userToken);
        Integer code = (Integer) userMap.get(BaseApiConstants.HTTP_CODE_NAME);
        if (code.equals(BaseApiConstants.HTTP_202_CODE)) { //用户登录信息过期
            return "202";
        }
        Map<String, Object> resultMap = (Map<String, Object>) ResultUtil.getResultMap(userMap);
        String json = new JSONObject().toJSONString(resultMap);
        UserEntity userEntity = new JSONObject().parseObject(json, UserEntity.class);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setUserId(userEntity.getId());
        paymentInfo.setTypeId(37L);
        paymentInfo.setOrderId(DateUtil.getTimeId());  //订单id生成规则:时间戳字符串
        paymentInfo.setPrice(Long.parseLong(price));
        paymentInfo.setSource("1");
        paymentInfo.setState("0");
        Map<String, Object> payTokenMap = payFeign.addPayInfoToken(paymentInfo);
        String token = (String) payTokenMap.get(BaseApiConstants.HTTP_DATA_NAME);
        return token;
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取会员商品数据
     * @Date 19:53 2019/7/15
     **/
    @GetMapping("/getAllVipItem.action")
    public @ResponseBody
    List<PaymentObject> getAllVipItem(HttpServletRequest request) {
        Map<String, Object> map = payFeign.getAllObject();
        List<PaymentObject> paymentObjectList = (List<PaymentObject>) ResultUtil.getResultMap(map);
        return paymentObjectList;
    }
}
