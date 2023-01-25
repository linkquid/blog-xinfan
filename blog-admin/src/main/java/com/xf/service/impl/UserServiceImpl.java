package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.mapper.UserMapper;
import com.xf.dao.pojo.Tag;
import com.xf.dao.pojo.User;
import com.xf.service.UserService;
import com.xf.utils.CopyUtils;
import com.xf.vo.PageResult;
import com.xf.vo.Result;
import com.xf.vo.UserVo;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CopyUtils copyUtils;

    @Override
    public Result listUser(PageParam pageParam) {
        Page<User> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getDeleted, 0);
        queryWrapper.orderByDesc(User::getRoleId);
        queryWrapper.orderByDesc(User::getLastLogin);
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);

        PageResult<UserVo> pageResult = new PageResult<>();
        pageResult.setTotal(userPage.getTotal());
        List<UserVo> userVos = copyUtils.userToVoList(userPage.getRecords());
        pageResult.setList(userVos);
        return Result.success(pageResult);
    }

    @Override
    public UserVo findAuthorById(Long authorId) {
        User user = userMapper.selectById(authorId);
        UserVo userVo = copyUtils.userToVo(user);
        return userVo;
    }
}
