package com.xf.service;

import com.xf.dao.pojo.Permission;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface PermissionService {
    /**
     * 分页查询所有权限信息
     * 分页查询
     * @param pageParam
     * @return
     */
    Result listPermission(PageParam pageParam);

    /**
     * 添加权限信息
     * @param permission
     * @return
     */
    Result add(Permission permission);

    /**
     * 更新权限信息
     * @param permission
     * @return
     */
    Result update(Permission permission);


    /**
     * 根据Id删除权限信息
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 查询所有的权限
     * @return
     */
    Result findAllPermissions();

}
