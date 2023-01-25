package com.xf.controller;

import com.xf.aop.Cache;
import com.xf.aop.MyLog;
import com.xf.service.ArticleService;
import com.xf.vo.Result;
import com.xf.vo.params.ArticleParam;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页展示文章
     * @param pageParam
     * @return
     */
    @PostMapping
    @CrossOrigin
    @MyLog(module = "文章", operator = "获取文章列表")
//    @Cache(expire = 5*60*1000, name = "list_article")
    public Result listArticle(@RequestBody PageParam pageParam) {
        return articleService.listArticle(pageParam);
    }

    /**
     * 展示最热门的文章
     * @return
     */
    @PostMapping("hot")
//    @Cache(expire = 5*60*1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 展示最新的文章
     * @return
     */
//    @Cache(expire = 1*60*1000, name = "new_article")
    @PostMapping("new")
    public Result newArticle() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 展示年度总结
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 展示文章详情
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章/更新文章
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        System.out.println(articleParam);
        if (articleParam.getId() == null) {
            //  发布文章
            return articleService.publish(articleParam);
        }
        //  更新文章
        return articleService.update(articleParam);
    }

    /**
     * 模糊搜索文章
     * @return
     */
    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam) {
        String articleName = articleParam.getSearch();
        return articleService.searchArticle(articleName);
    }

}
