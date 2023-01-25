package com.xf.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xf.dao.mapper.ArticleMapper;
import com.xf.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 多线程服务
 */
@Service
public class ThreadService {

    /**
     * 增加文章浏览量
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void incrArticleViewCount(ArticleMapper articleMapper, Article article) {
        article.setViewCounts(article.getViewCounts() + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, article.getViewCounts()-1);
        articleMapper.update(article, updateWrapper);
        try {
            Thread.sleep(2000);
            System.out.println("更新完成！！！！！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
