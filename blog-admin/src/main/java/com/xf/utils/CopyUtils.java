package com.xf.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xf.dao.mapper.RoleMapper;
import com.xf.dao.pojo.Article;
import com.xf.dao.pojo.Permission;
import com.xf.dao.pojo.Role;
import com.xf.dao.pojo.User;
import com.xf.vo.ArticleVo;
import com.xf.vo.PermissionVo;
import com.xf.vo.RoleVo;
import com.xf.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 类型转换工具
 */
@Configuration
public class CopyUtils {
    @Autowired
    private RoleMapper roleMapper;

    public UserVo userToVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);

        Date date = new Date(user.getLastLogin());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        userVo.setLastLogin(dateFormat.format(date));
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId, user.getRoleId());
        queryWrapper.last("limit 1");
        Role role = roleMapper.selectOne(queryWrapper);
        userVo.setRoleName(role.getName());
        return userVo;
    }

    public List<UserVo> userToVoList(List<User> users) {
        List<UserVo> res = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<>());
        HashMap<Long, String> map = new HashMap<>();
        for (Role role : roles) {
            map.put(role.getId(), role.getName());
        }
        for (User user : users) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);

            Date date = new Date(user.getLastLogin());
            userVo.setLastLogin(dateFormat.format(date));
            userVo.setRoleName(map.get(user.getRoleId()));
            res.add(userVo);
        }
        return res;
    }

    public RoleVo roleToVo(Role role) {
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        List<Permission> permissions = roleMapper.findPermissionByRoleId(role.getId());
        List<Long> perm = new ArrayList<>();
        for (Permission permission : permissions) {
            perm.add(permission.getId());
        }
        roleVo.setPermissions(perm);
        return roleVo;
    }

    public List<RoleVo> roleToVoList(List<Role> roles) {
        List<RoleVo> roleVos = new ArrayList<>();
        for (Role role : roles) {
            roleVos.add(roleToVo(role));
        }
        return roleVos;
    }

    public PermissionVo permissionToVo(Permission permission) {
        PermissionVo permissionVo = new PermissionVo();
        permissionVo.setId(permission.getId());
        permissionVo.setName(permission.getName());
        return permissionVo;
    }

    public List<PermissionVo> permissionToVoList(List<Permission> permissions) {
        List<PermissionVo> permissionVos = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionVos.add(permissionToVo(permission));
        }
        return permissionVos;
    }


}
