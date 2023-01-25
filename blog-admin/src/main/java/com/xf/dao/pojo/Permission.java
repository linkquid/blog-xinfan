package com.xf.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 权限类
 */

@Data
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String path;
    private String description;
    private String method;

}
