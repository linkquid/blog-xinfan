package com.xf.service;

import com.xf.dao.pojo.Tag;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface TagService {

    /**
     * 分页查询标签信息
     * @param pageParam
     * @return
     */
    Result listTags(PageParam pageParam);

    /**
     * 更新标签信息
     * @param tag
     * @return
     */
    Result updateTag(Tag tag);

    /**
     * 通过tagId删除标签信息
     * @param id
     * @return
     */
    Result deleteByTagId(Long id);

    /**
     * 添加标签信息
     * @param tag
     * @return
     */
    Result addTag(Tag tag);
}
