package com.xf.service;

import com.xf.vo.Result;
import com.xf.vo.params.ArticleParam;
import com.xf.vo.params.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 查询最热门的文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);

    /**
     * 查询最新的文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 年度总结
     * @return
     */
    Result listArchives();

    /**
     * 查询文章详细内容
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发表功能
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);

    /**
     * 更新文章功能
     * @param articleParam
     * @return
     */
    Result update(ArticleParam articleParam);

    /**
     * 模糊查询文章
     * @param articleName
     * @return
     */
    Result searchArticle(String articleName);
}
