package com.xf.service;

import com.xf.dao.pojo.SysUser;
import com.xf.vo.Result;
import com.xf.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Transactional
public interface LoginService {

    /**
     * 登陆功能
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 检验token信息
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登陆
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册功能
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
