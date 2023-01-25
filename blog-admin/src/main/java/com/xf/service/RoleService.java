package com.xf.service;

import com.xf.vo.PermissionVo;
import com.xf.vo.Result;
import com.xf.vo.RoleVo;
import com.xf.vo.params.PageParam;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface RoleService {
    /**
     * 列出所有角色的所有权限
     * @return
     */
    Result listRole(PageParam pageParam);

    /**
     * 更新角色的名称/权限
     * @param roleVo
     * @return
     */
    Result updateRole(RoleVo roleVo);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    Result deleteRole(Long roleId);

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    Result addRole(RoleVo roleVo);
}
