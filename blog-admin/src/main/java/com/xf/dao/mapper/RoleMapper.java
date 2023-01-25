package com.xf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xf.dao.pojo.Permission;
import com.xf.dao.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过 roleId 查询所有权限
     * @param adminId
     * @return
     */
    @Select("select * from ms_permission where id in (select permission_id from ms_role_permission where role_id = #{roleId})")
    List<Permission> findPermissionByRoleId(Long adminId);
}
