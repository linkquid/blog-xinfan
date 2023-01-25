package com.xf.aop;

import com.alibaba.fastjson.JSON;
import com.xf.utils.HttpContextUtils;
import com.xf.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 日志切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.xf.aop.MyLog)")
    public void pt() {

    }

    /**
     * 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, time);
        return  result;
    }

    /**
     * 保存日志
     * @param joinPoint
     * @param time
     */
    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyLog myLog = method.getAnnotation(MyLog.class);
        log.info("=====================log start=========================");
        log.info("module : {}", myLog.module());
        log.info("operator : {}", myLog.operator());

        //  请求方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method : {}", className + "." + methodName + "()");

        //  请求参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        try {
            params = JSON.toJSONString(args[0]);
        } catch (Exception e) {
            log.info("{}", e);
        }
        log.info("params : {}", params);

        //  IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip : {}", IPUtils.getIpAddr(request));

        log.info("execute time: {} ms", time);
        log.info("=====================log end===========================");

    }

}
