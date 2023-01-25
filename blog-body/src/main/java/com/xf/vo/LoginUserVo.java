package com.xf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 登陆信息类
 */
@Data
public class LoginUserVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String account;
    private String password;
    private String avatar;

}
