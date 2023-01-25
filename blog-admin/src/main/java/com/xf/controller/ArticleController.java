package com.xf.controller;

import com.xf.service.ArticleService;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("list")
    public Result listArticle(@RequestBody PageParam pageParam) {
        return articleService.listArticle(pageParam);
    }

    @PostMapping("approval")
    public Result listApproval(@RequestBody PageParam pageParam) {
        return articleService.listApproval(pageParam);
    }

    @GetMapping("delete/{id}")
    public Result deleteRole(@PathVariable("id") Long articleId) {
        return articleService.deleteByArticleId(articleId);
    }

    @GetMapping("agree/{id}")
    public Result agreeArticle(@PathVariable("id") Long articleId) {
        return articleService.agreeArticleById(articleId);
    }

    @GetMapping("disagree/{id}")
    public Result disagreeArticle(@PathVariable("id") Long articleId) {
        return articleService.disagreeArticleById(articleId);
    }

}
