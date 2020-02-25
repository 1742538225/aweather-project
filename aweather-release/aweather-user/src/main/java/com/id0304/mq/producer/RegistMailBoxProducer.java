package com.id0304.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

//往消息服务推送邮件信息,消息队列生产者
@Service("registerMailboxProducer")
public class RegistMailBoxProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, String json){
        jmsMessagingTemplate.convertAndSend(destination,json);
    }
}
