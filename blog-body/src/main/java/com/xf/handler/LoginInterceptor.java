package com.xf.handler;

import com.alibaba.fastjson.JSON;
import com.xf.dao.pojo.SysUser;
import com.xf.service.LoginService;
import com.xf.utils.UserThreadLocal;
import com.xf.vo.ErrorCode;
import com.xf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 登陆拦截器
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  1.判断请求的接口路径，是否为 HandlerMethod （controller方法）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //  2.判断 Token 是否为空（登陆）
        String token = request.getHeader("Authorization");

        //  打印日志
        log.info("========================request start===============================");
        log.info("request uri:{}", request.getRequestURI());
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("========================request end=================================");

        if (!StringUtils.hasText(token)) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        //  3.不为空，验证 Token
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        //  4.本地线程存储用户信息
        UserThreadLocal.set(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**
         * 用完记得删除！ThreadLocal
         * 避免内存泄漏！
         */
        UserThreadLocal.remove();
    }
}
