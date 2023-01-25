package com.xf.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Data
public class Role {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

}
