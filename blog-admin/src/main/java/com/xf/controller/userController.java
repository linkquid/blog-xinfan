package com.xf.controller;

import com.xf.dao.mapper.UserMapper;
import com.xf.service.UserService;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@RestController
@RequestMapping("user")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("list")
    public Result listUser(@RequestBody PageParam pageParam) {
        return userService.listUser(pageParam);
    }

}
