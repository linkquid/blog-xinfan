package com.xf.service;

import com.xf.dao.pojo.ArticleBody;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface ArticleBodyService {

    /**
     * 通过文章ID查找内容
     * @param articleId
     * @return
     */
    ArticleBody findBodyByArticleId(Long articleId);

    /**
     * 通过文章Id删除内容
     * @param articleId
     */
    void deleteByArticleId(Long articleId);
}
