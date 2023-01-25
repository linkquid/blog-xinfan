package com.xf.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Data
public class UserVo {
    @TableId(type = IdType.ASSIGN_ID)   //  默认
    private Long id;
    private String roleName;  //  角色
    private String avatar;  //  头像
    private String lastLogin;
    private String nickName;    //  昵称
}
