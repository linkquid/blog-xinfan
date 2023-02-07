package com.xf.service.impl;

import com.alibaba.fastjson.JSON;
import com.xf.dao.pojo.SysUser;
import com.xf.service.EmailService;
import com.xf.service.LoginService;
import com.xf.service.SysUserService;
import com.xf.utils.JWTUtils;
import com.xf.vo.ErrorCode;
import com.xf.vo.Result;
import com.xf.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final String SALT = "mszlu!@#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        //  1.检查参数是否合法？
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
            return Result.fail(ErrorCode.PARAM_ERROR);
        }
        //  2.redis是否存在token？

        //  3.不存在，查询user表是否存在？
        SysUser sysUser = sysUserService.findUser(account);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);
        }
        password = DigestUtils.md5Hex("xf" + password + sysUser.getSalt());
        if (!sysUser.getPassword().equals(password)) {
            return Result.fail(ErrorCode.PASS_ERROR);
        }
        //  4.如果存在，使用jwt生成token
        String token = JWTUtils.createToken(sysUser.getId());
        //  5.token存入redis缓存
        redisTemplate.opsForValue().set("TOKEN-" + token, JSON.toJSONString(sysUser), 12, TimeUnit.HOURS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN-" + token);
        if (!StringUtils.hasText(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN-" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        //  1.判断参数是否合法？
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        String email = loginParam.getEmail();
        String code = loginParam.getCode();
        System.out.println(email);
        System.out.println(code);
        if (!(StringUtils.hasText(account)
                && StringUtils.hasText(password)
                && StringUtils.hasText(nickname)
                && EmailServiceImpl.isEmail(email))
                && code.length() == 6) {
            return Result.fail(ErrorCode.PARAM_ERROR);
        }
        //  2.判断验证码是否正确？
        String codeTrue = redisTemplate.opsForValue().get("Email:"+email);
        System.out.println(codeTrue);
        System.out.println(code);
        if (codeTrue == code) {
            return Result.fail(ErrorCode.CODE_ERROR);
        }
        //  3.判断账号是否存在？
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        //  4.不存在则注册账号
        SysUser user = defaultUser(loginParam);
        sysUserService.save(user);
        //  5.生成Token，存入Redis
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN-" + token, JSON.toJSONString(user), 1, TimeUnit.HOURS);
        //  6.事务处理，失败回滚
        return Result.success(token);
    }

    /**
     * 注册用户初始信息
     * @param loginParam
     * @return
     */
    public SysUser defaultUser(LoginParam loginParam) {
        String salt = (Math.random()+"").substring(3, 7);
        SysUser sysUser = new SysUser();
        sysUser.setAccount(loginParam.getAccount());
        sysUser.setNickName(loginParam.getNickname());
        String MD5Pwd = DigestUtils.md5Hex("xf" + loginParam.getPassword() + salt);
        System.out.println(MD5Pwd);

        sysUser.setPassword(MD5Pwd);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/cz.jpg");
        sysUser.setRoleId(0);    //  1为管理员
        sysUser.setDeleted(0);  //  0为记录存在
        sysUser.setSalt(salt);
        sysUser.setStatus("");
        sysUser.setMobilePhoneNumber("");
        sysUser.setEmail(loginParam.getEmail());
        return sysUser;
    }
}
