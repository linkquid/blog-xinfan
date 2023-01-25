package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.mapper.RoleMapper;
import com.xf.dao.mapper.RolePermissionMapper;
import com.xf.dao.pojo.Permission;
import com.xf.dao.pojo.Role;
import com.xf.dao.pojo.RolePermission;
import com.xf.service.AdminService;
import com.xf.service.PermissionService;
import com.xf.service.RoleService;
import com.xf.utils.CopyUtils;
import com.xf.vo.PageResult;
import com.xf.vo.Result;
import com.xf.vo.RoleVo;
import com.xf.vo.params.PageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private CopyUtils copyUtils;

    @Override
    public Result listRole(PageParam pageParam) {
        Page<Role> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        Page<Role> rolePage = roleMapper.selectPage(page, new LambdaQueryWrapper<>());

        PageResult<RoleVo> pageResult = new PageResult<>();
        pageResult.setTotal(rolePage.getTotal());
        List<Role> records = rolePage.getRecords();
        pageResult.setList(copyUtils.roleToVoList(records));

        return Result.success(pageResult);
    }

    @Override
    @Transactional
    public Result updateRole(RoleVo roleVo) {
        if (roleVo.getId() == null || !StringUtils.hasText(roleVo.getName())) {
            return Result.fail(1001, "ID和名称不能为空");
        }
        if (roleVo.getPermissions() != null) {
            Role role = new Role();
            role.setId(roleVo.getId());
            role.setName(roleVo.getName());
            //  更新name
            roleMapper.updateById(role);
            //  先删除权限
            LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(RolePermission::getRoleId, roleVo.getId());
            rolePermissionMapper.delete(queryWrapper);
            //  再添加权限
            for (Long permission : roleVo.getPermissions()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permission);
                rolePermission.setRoleId(roleVo.getId());
                rolePermissionMapper.insert(rolePermission);
            }
        }
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result deleteRole(Long roleId) {
        if (roleId == null) {
            return Result.fail(1001, "ID不能为空！");
        }
        //  1.删除权限列表
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(queryWrapper);
        //  2.删除角色
        roleMapper.deleteById(roleId);
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result addRole(RoleVo roleVo) {
        if (!StringUtils.hasText(roleVo.getName())) {
            return Result.fail(1001, "名字不能为空！");
        }
        //  1.添加用户
        Role role = new Role();
        role.setName(roleVo.getName());
        roleMapper.insert(role);
        return Result.success(null);
    }

}
