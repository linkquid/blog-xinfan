package com.xf.service;

import com.xf.dao.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /**
         * 登陆时，会将username 传递到这
         * 通过username 查询 admin表
         * admin存在。将密码传给SpringSecurity
         * admin不存在，返回null，认证失败
         */
        Admin admin = adminService.findAdminByUsername(s);
        if (admin == null) {
            return null;
        }
        UserDetails userDetails = new User(s, admin.getPassword(), new ArrayList<>());
        return userDetails;
    }
}
