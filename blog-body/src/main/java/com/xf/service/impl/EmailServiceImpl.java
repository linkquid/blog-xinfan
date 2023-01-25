package com.xf.service.impl;

import com.xf.dao.pojo.SysUser;
import com.xf.service.EmailService;
import com.xf.service.SysUserService;
import com.xf.vo.ErrorCode;
import com.xf.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Pattern;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JavaMailSender mailSender;  //  邮箱发送对象

    @Value("${spring.mail.username}")   //  获取发送账号
    private String adminEmail;

    @Override
    public Result getCode(String email) throws MessagingException {
        if (!isEmail(email)) {
            return Result.fail(10022, "邮箱格式错误！");
        }
        SysUser sysUser = sysUserService.findUserByEmail(email);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        /**
         * 1.生成code码
         * 2.创建邮件对象
         * 3.创建信息编辑对象
         * 4.发送邮件
         * 5.返回生成的邮件验证码
         */
        String code = (Math.random() + "").substring(3, 9);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        helper.setFrom(adminEmail); //  发送者
        helper.setTo(email);    //  接收者
        helper.setSubject("[心烦博客]用户注册服务");  //  标题
        helper.setText("您好！您的验证码为：" + code + " ，15分钟内有效。"); //  内容
        mailSender.send(mimeMessage);
        redisTemplate.opsForValue().set("Email:"+email, code, 15 * 60);
        System.out.println(code);
        return Result.success(null);
    }

    /**
     * 判断是否是Email格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

}
