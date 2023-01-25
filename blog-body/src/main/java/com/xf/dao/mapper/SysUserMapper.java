package com.xf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xf.dao.pojo.SysUser;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByArticleId(Long id);

}
