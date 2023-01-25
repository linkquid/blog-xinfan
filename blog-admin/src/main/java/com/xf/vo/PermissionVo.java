package com.xf.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 权限参数
 */
@Data
public class PermissionVo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;

}
