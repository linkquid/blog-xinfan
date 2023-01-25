package com.xf.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xf.dao.pojo.Permission;
import lombok.Data;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 角色及权限参数
 */
@Data
public class RoleVo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private List<Long> permissions;

}
