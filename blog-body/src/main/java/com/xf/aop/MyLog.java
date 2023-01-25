package com.xf.aop;

import java.lang.annotation.*;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 日志注解
 */

@Target({ElementType.TYPE, ElementType.METHOD}) //  Type:类上使用   Method：方法上使用
@Retention(RetentionPolicy.RUNTIME) //  运行时
@Documented //
public @interface MyLog {

    String module() default "";
    String operator() default "";



}
