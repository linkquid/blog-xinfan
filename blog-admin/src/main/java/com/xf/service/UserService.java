package com.xf.service;

import com.xf.dao.pojo.User;
import com.xf.vo.Result;
import com.xf.vo.UserVo;
import com.xf.vo.params.PageParam;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface UserService {
    /**
     * 分页查询所有的用户
     * @param pageParam
     * @return
     */
    Result listUser(PageParam pageParam);

    /**
     * 通过ID查询作者信息
     * @return
     */
    UserVo findAuthorById(Long authorId);
}
