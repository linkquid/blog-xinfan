package com.xf.service;

import com.xf.vo.Result;

import javax.mail.MessagingException;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface EmailService {

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    Result getCode(String email) throws MessagingException;

    /**
     * 检查是否满足注册条件
     * @param email
     * @return
     */
    Result checkRegister(String email);

}
