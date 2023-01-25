package com.xf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 返回前端类型
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

//    public static Result fail(ErrorCode errorCode) {
//        return new Result(false, errorCode.getCode(), errorCode.getMsg(), null);
//    }

}
