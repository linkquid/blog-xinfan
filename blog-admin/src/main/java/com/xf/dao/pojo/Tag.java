package com.xf.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 标签类
 */
@Data
public class Tag {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String avatar;
    private String tagName;

}
