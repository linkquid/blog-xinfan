package com.xf.service;

import com.xf.dao.pojo.Admin;
import com.xf.dao.pojo.Permission;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface AdminService {

    /**
     * 通过 username 查找Admin
     * @param username
     * @return
     */
    Admin findAdminByUsername(String username);

    /**
     * 通过 AdminId 查找所有权限
     * @param id
     * @return
     */
    List<Permission> findPermissionByAdminId(Long id);
}
