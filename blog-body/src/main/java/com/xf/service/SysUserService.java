package com.xf.service;

import com.xf.dao.pojo.SysUser;
import com.xf.vo.Result;
import com.xf.vo.UserVo;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface SysUserService {
    /**
     * 查询文章的作者
     * @return
     */
    SysUser findUserByArticleId(Long articleId);

    /**
     * 查询用户是否存在
     * @param account
     * @return
     */
    SysUser findUser(String account);

    /**
     * 通过Token查询用户信息
     * @param token
     */
    Result findUserByToken(String token);

    /**
     * 通过账号查询用户信息
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 新增用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 通过id查询用户的详细信息
     * @param id
     * @return
     */
    UserVo findUserVoByAuthorId(Long id);

    /**
     * sysUser To UserVo
     * @param sysUser
     * @return
     */
    UserVo copy(SysUser sysUser);

    /**
     * 通过Email查询用户
     * @param email
     * @return
     */
    SysUser findUserByEmail(String email);
}
