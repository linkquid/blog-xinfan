package com.xf.vo;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 错误码枚举类
 */
public enum  ErrorCode {

    PARAM_ERROR(10001, "参数错误！"),
    ACCOUNT_PWD_NOT_EXIST(10002, "账号或密码不存在！"),
    ACCOUNT_EXIST(10003, "账号或邮箱已经存在！"),
    TOKEN_ERROR(10004, "token为空！"),
    NO_PERMISSION(70001, "没有访问权限！"),
    SESSION_TIME_OUT(90001, "会话超时！"),
    NO_LOGIN(90002, "请先登陆！"),
    CODE_ERROR(10005, "验证码错误！"),
    PASS_ERROR(10006, "密码错误！")
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
