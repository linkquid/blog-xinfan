package com.xf.vo.params;

import com.xf.vo.Result;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 登陆信息参数
 */
@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;

    private String email;

    private String code;

}
