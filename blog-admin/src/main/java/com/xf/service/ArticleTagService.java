package com.xf.service;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface ArticleTagService {
    /**
     * 通过文章ID删除对应关联
     * @param articleId
     */
    void deleteByArticleId(Long articleId);

    /**
     * 通过标签ID删除对应关联
     * @param id
     */
    void deleteByTagId(Long id);
}
