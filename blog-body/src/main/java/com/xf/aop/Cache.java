package com.xf.aop;

import java.lang.annotation.*;
import java.util.Random;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 缓存
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    //  过期时间
    long expire() default  1 * 60 * 1000;
    //  缓存Key
    String name() default "";

}
