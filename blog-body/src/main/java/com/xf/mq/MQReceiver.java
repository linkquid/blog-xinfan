package com.xf.mq;

import com.xf.service.EmailService;
import com.xf.vo.Result;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : MQ消费者
 */
@Component
public class MQReceiver {
    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    private void sendEmail(String email) throws MessagingException {
        emailService.getCode(email);
    }

}
