package com.xf.service;

import com.xf.vo.Result;
import com.xf.vo.params.PageParam;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface ArticleService {
    /**
     * 分页查询文章
     * @param pageParam
     * @return
     */
    Result listArticle(PageParam pageParam);

    /**
     * 通过ArticleId删除文章
     * @param articleId
     * @return
     */
    Result deleteByArticleId(Long articleId);

    /**
     * 文章审批通过
     * @param articleId
     * @return
     */
    Result agreeArticleById(Long articleId);

    /**
     * 文章审批不通过
     * @param articleId
     * @return
     */
    Result disagreeArticleById(Long articleId);

    /**
     * 分页查询待审批列表
     * @param pageParam
     * @return
     */
    Result listApproval(PageParam pageParam);
}
