package com.id0304.service;

import com.alibaba.fastjson.JSONObject;
import com.id0304.adapter.MessageAdapter;
import com.id0304.constants.RegistMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class SMSMailBoxService implements MessageAdapter {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void distribute(JSONObject jsonObject) {
        String email = (String) jsonObject.get("email");
        log.info("###消费者收到消息,开始发送邮件...mail:{}",email);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            //传入两个参数,一个复杂邮件,一个布尔类型是指定有无附件,true表示有附件
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setSubject(RegistMail.MAIL_SUBJECT);
            //可以使用html标签css样式,如果是html语句,要记得还要传入一个布尔类型true,不然是以普通文本发送
            mimeMessageHelper.setText(RegistMail.MAIL_TEXT,true);
            //上传两个附件
//            mimeMessageHelper.addAttachment("1.jpg", new File("C:\\Users\\xianyu\\Desktop\\1.jpg"));
//            mimeMessageHelper.addAttachment("2.jpg", new File("C:\\Users\\xianyu\\Desktop\\2.jpg"));
            //指定接收方
            mimeMessageHelper.setTo(email);
            //指定发送方
            mimeMessageHelper.setFrom(RegistMail.MAIL_SENDER);
        } catch (MessagingException e) {
            log.error("#####发送邮件失败");
            e.printStackTrace();
        }

        //发送邮件
        javaMailSender.send(mimeMessage);
    }
}
