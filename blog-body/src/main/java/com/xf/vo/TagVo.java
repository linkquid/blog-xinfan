package com.xf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 标签信息
 */
@Data
public class TagVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String avatar;
    private String tagName;

}
