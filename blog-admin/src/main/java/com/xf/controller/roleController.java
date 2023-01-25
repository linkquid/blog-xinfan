package com.xf.controller;


import com.xf.service.PermissionService;
import com.xf.service.RoleService;
import com.xf.vo.Result;
import com.xf.vo.RoleVo;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@RestController
@RequestMapping("role")
public class roleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @PostMapping("list")
    public Result listRole(@RequestBody PageParam pageParam) {
        return roleService.listRole(pageParam);
    }

    @PostMapping("AllPermissions")
    public Result AllPermissions() {
        return permissionService.findAllPermissions();
    }

    @PostMapping("update")
    public Result updateRole(@RequestBody RoleVo roleVo) {
        return roleService.updateRole(roleVo);
    }

    @PostMapping("add")
    public Result addRole(@RequestBody RoleVo roleVo) {
        return roleService.addRole(roleVo);
    }

    @GetMapping("delete/{id}")
    public Result deleteRole(@PathVariable("id") Long RoleId) {
        return roleService.deleteRole(RoleId);
    }

}
