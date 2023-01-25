package com.xf.service;

import com.xf.vo.Result;
import com.xf.vo.TagVo;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface TagService {

    /**
     * 通过 文章id 查询所有标签
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询最热门的标签
     * @param limit
     * @return
     */
    Result hots(int limit);

    /**
     * 查询所有的文章标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有标签的详细信息
     * @return
     */
    Result findAllDetail();

    /**
     * 通过Id查询标签
     * @param id
     * @return
     */
    Result findTagById(Long id);
}
