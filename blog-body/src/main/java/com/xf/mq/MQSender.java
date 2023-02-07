package com.xf.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : MQ生产者
 */
@Component
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmail(String email) {
        log.info("邮箱发送验证码：" + email);
        rabbitTemplate.convertAndSend("emailExchanger", "email.verify.code", email);
    }

}
