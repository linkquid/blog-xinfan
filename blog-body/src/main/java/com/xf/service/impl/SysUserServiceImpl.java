package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xf.dao.mapper.SysUserMapper;
import com.xf.dao.pojo.SysUser;
import com.xf.service.LoginService;
import com.xf.service.SysUserService;
import com.xf.vo.ErrorCode;
import com.xf.vo.LoginUserVo;
import com.xf.vo.Result;
import com.xf.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserByArticleId(Long articleId) {
        SysUser sysUser = sysUserMapper.selectByArticleId(articleId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickName("心烦");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        //  1.Token合法性校验（判空、redis缓存）
        //  2.失败返回错误
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR);
        }
        //  3.成功返回LoginUserVo
        LoginUserVo loginUserVo = userToLoginUserVo(sysUser);
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        /**
         * 使用雪花算法自动生成id
         */
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoByAuthorId(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickName("心烦");
        }
        UserVo userVo = this.copy(sysUser);
        return userVo;
    }

    public LoginUserVo userToLoginUserVo(SysUser sysUser) {
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setPassword(sysUser.getPassword());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return loginUserVo;
    }

    public UserVo copy(SysUser sysUser) {
        UserVo userVo = new UserVo();
        userVo.setId(sysUser.getId());
        userVo.setNickname(sysUser.getNickName());
        userVo.setAvatar(sysUser.getAvatar());
        return userVo;
    }

    @Override
    public SysUser findUserByEmail(String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getEmail, email);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }
}
