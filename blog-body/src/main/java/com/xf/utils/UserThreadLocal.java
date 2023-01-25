package com.xf.utils;

import com.xf.dao.pojo.SysUser;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 本地线程存储用户信息
 */
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void set(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
