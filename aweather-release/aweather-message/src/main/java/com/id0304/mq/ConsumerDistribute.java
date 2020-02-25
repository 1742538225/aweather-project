package com.id0304.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.id0304.adapter.MessageAdapter;
import com.id0304.constants.MQContants;
import com.id0304.service.SMSMailBoxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

//消息消费者,监听队列,判断消息类型,调用对应的服务消费消息
@Slf4j
@Service
public class ConsumerDistribute {
    @Autowired
    SMSMailBoxService smsMailBoxService;

    //监听队列,取到json字符串
    @JmsListener(destination = MQContants.QUEUE_NAME_MAIL)
    public void distribute(String json) {
        log.info("###消息服务收到消息,消息内容json:{}",json);
        if(StringUtils.isEmpty(json)){
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject header = jsonObject.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        MessageAdapter messageAdapter = null;
        switch (interfaceType){
            case MQContants.SMS_MAIL:
                messageAdapter = smsMailBoxService;
                break;
            default:
                break;
        }
        JSONObject content = jsonObject.getJSONObject("content");
        messageAdapter.distribute(content);
        log.info("#####成功发送邮件!");
    }
}
