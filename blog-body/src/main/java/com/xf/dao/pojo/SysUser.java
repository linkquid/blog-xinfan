package com.xf.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 用户类
 */
@Data
public class SysUser {

    @TableId(type = IdType.ASSIGN_ID)   //  默认
    private Long id;
    private String account; //  账号
    private Integer roleId;  //  角色
    private String avatar;  //  头像
    private Long createDate;
    private Integer deleted;    //  是否删除
    private String email;
    private Long lastLogin;
    private String mobilePhoneNumber;  //   手机号
    private String nickName;    //  昵称
    private String password;
    private String salt;
    private String status;  //  状态

}
