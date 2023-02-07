package com.xf.controller;

import com.xf.mq.MQSender;
import com.xf.service.EmailService;
import com.xf.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    @GetMapping
    public Result getCode(String email) throws MessagingException {
        System.out.println(email);
        return emailService.checkRegister(email);
    }



}
