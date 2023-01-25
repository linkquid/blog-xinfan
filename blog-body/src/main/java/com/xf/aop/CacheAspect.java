package com.xf.aop;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 缓存切面
 */
@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.xf.aop.Cache)")
    public void pt() {

    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint point) {
        try {
            Signature signature = point.getSignature();
            String className = point.getTarget().getClass().getSimpleName();
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[point.getArgs().length];
            Object[] args = point.getArgs();

            String params = "";
            for (int i=0; i<args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                /**
                 * 加密处理
                 * 防止key过长或字符转义问题
                 */
                params = DigestUtils.md5Hex(params);
            }
            Method method = point.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //  获取Cache注解
            Cache cache = method.getDeclaredAnnotation(Cache.class);
            long expire = cache.expire();
            String name = cache.name();
            //  从redis中获取
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)) {
                log.info("从缓存中取出----------{}", redisKey);
                Result result = JSON.parseObject(redisValue, Result.class);
                return result;
            }

            Object proceed = point.proceed();
            redisTemplate.opsForValue().set(redisKey, new ObjectMapper().writeValueAsString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存--------{}：{}", className, methodName);
            return proceed;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999, "系统异常！");
    }


}
