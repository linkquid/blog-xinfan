package com.xf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 评论用户信息
 */
@Data
public class UserVo {

    private String nickname;
    private String avatar;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

}
