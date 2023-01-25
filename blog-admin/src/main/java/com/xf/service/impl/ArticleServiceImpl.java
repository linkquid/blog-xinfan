package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.mapper.ArticleMapper;
import com.xf.dao.pojo.Article;
import com.xf.dao.pojo.ArticleBody;
import com.xf.dao.pojo.User;
import com.xf.service.*;
import com.xf.vo.*;
import com.xf.vo.params.PageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public Result listArticle(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getState, 1);
        queryWrapper.orderByDesc(Article::getUpdateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);

        PageResult<ArticleVo> pageResult = new PageResult<>();
        pageResult.setTotal(articlePage.getTotal());
        List<Article> records = articlePage.getRecords();
        pageResult.setList(copyList(records));

        return Result.success(pageResult);
    }

    @Override
    @Transactional
    public Result deleteByArticleId(Long articleId) {
        //  1.删除对应Tags
        articleTagService.deleteByArticleId(articleId);
        //  2.删除body内容
        articleBodyService.deleteByArticleId(articleId);
        //  3.删除article文章
        articleMapper.deleteById(articleId);
        return Result.success(null);
    }

    @Override
    public Result agreeArticleById(Long articleId) {
        articleMapper.agreeArticleById(articleId);
        return Result.success(null);
    }

    @Override
    public Result disagreeArticleById(Long articleId) {
        articleMapper.disagreeArticleById(articleId);
        return Result.success(null);
    }

    @Override
    public Result listApproval(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getState, 0);
        queryWrapper.orderByDesc(Article::getUpdateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);

        PageResult<ArticleVo> pageResult = new PageResult<>();
        pageResult.setTotal(articlePage.getTotal());
        List<Article> records = articlePage.getRecords();
        pageResult.setList(copyList(records));

        return Result.success(pageResult);
    }

    public ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        ArticleBody body = articleBodyService.findBodyByArticleId(article.getId());
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(body.getContent());
        articleVo.setBody(articleBodyVo);

        UserVo author = userService.findAuthorById(article.getAuthorId());
        articleVo.setAuthor(author);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        articleVo.setCreateDate(dateFormat.format(article.getCreateDate()));
        articleVo.setUpdateDate(dateFormat.format(article.getUpdateDate()));
        return articleVo;
    }

    public List<ArticleVo> copyList(List<Article> articles) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            articleVos.add(copy(article));
        }
        return articleVos;
    }
}
