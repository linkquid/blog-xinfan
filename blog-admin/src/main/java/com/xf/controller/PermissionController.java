package com.xf.controller;

import com.xf.dao.pojo.Permission;
import com.xf.service.AdminService;
import com.xf.service.PermissionService;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permissionList")
    public Result listPermission(@RequestBody PageParam pageParam) {
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("add")
    public Result add(@RequestBody Permission permission) {
        return permissionService.add(permission);
    }

    @PostMapping("update")
    public Result update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @GetMapping("delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return permissionService.delete(id);
    }



}
