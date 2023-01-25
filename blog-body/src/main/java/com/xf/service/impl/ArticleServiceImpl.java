package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.dos.Archives;
import com.xf.dao.mapper.ArticleBodyMapper;
import com.xf.dao.mapper.ArticleMapper;
import com.xf.dao.mapper.ArticleTagMapper;
import com.xf.dao.mapper.CategoryMapper;
import com.xf.dao.pojo.*;
import com.xf.service.ArticleService;
import com.xf.service.SysUserService;
import com.xf.service.TagService;
import com.xf.service.ThreadService;
import com.xf.utils.UserThreadLocal;
import com.xf.vo.*;
import com.xf.vo.params.ArticleParam;
import com.xf.vo.params.PageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ThreadService threadService;

    @Override
    public Result listArticle(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticles(
                page,
                pageParam.getCategoryId(),
                pageParam.getTagId(),
                pageParam.getYear(),
                pageParam.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true, false, false));
    }

    /*
    @Override
    public Result listArticle(PageParam pageParam) {
        //  1.分页查询
        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //  2.是否需要按类别查询？
        if (pageParam.getCategoryId() != null) {
            queryWrapper.eq(Article::getCategoryId, pageParam.getCategoryId());
        }
        //  3.是否需要按标签查询？
        List<Long> articleIdList = new ArrayList<>();
        if (pageParam.getTagId() != null) {
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParam.getTagId());
            List<ArticleTag> articleTagList = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTagList) {
                //  添加含有该标签的所有文章Id
                articleIdList.add(articleTag.getArticleId());
            }
            //  范围查询
            if (articleIdList.size() > 0) {
                queryWrapper.in(Article::getId, articleIdList);
            } else {
                return Result.success(new ArrayList<ArticleVo>());
            }
        }
        //  4.按置顶、创建时间降序
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records, true, true, false, false);

        return Result.success(articleVoList);
    }
     */

    @Override
    public Result hotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        //  1.根据id查询文章信息
        Article article = articleMapper.selectById(articleId);
        //  2.根据bodyId和categoryId 关联查询
        ArticleVo articleVo = copy(article, true, true, true, true);
        /**
         * 通过线程池完成 多线程下 浏览量的增加
         */
        threadService.incrArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        //  1.构建Article对象
        Article article = new Article();
        //  2.获取作者Id,设置默认值
        SysUser sysUser = UserThreadLocal.get();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setUpdateDate(article.getCreateDate());
        article.setCategoryId(articleParam.getCategory().getId());
        //  3.插入自动生成文章Id
        articleMapper.insert(article);
        //  4.标签处理
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        //  5.内容处理,自动生成bodyId
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        //  6.更新文章的bodyId
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        //  7.转成JSON格式，也可以返回Vo对象
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    @Override
    @Transactional
    public Result update(ArticleParam articleParam) {
        //  1.获取文章内容
        Article article = articleMapper.selectById(articleParam.getId());
        //  2.更新时间、标题、简介、类别
        article.setUpdateDate(System.currentTimeMillis());
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCategoryId(articleParam.getCategory().getId());
        articleMapper.updateById(article);
        //  3.先删除再插入新标签
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleParam.getId());
        articleTagMapper.delete(queryWrapper);
        List<TagVo> tags = articleParam.getTags();
        if (tags != null && tags.size() > 0) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleParam.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        //  4.更新文章body内容
        boolean update = new LambdaUpdateChainWrapper<ArticleBody>(articleBodyMapper)
                .eq(ArticleBody::getId, article.getBodyId())
                .set(ArticleBody::getContent, articleParam.getBody().getContent())
                .set(ArticleBody::getContentHtml, articleParam.getBody().getContentHtml())
                .update();
        //  5.转成JSON格式，也可以返回Vo对象
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    @Override
    public Result searchArticle(String articleName) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.like(Article::getTitle, articleName);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false, false, false));
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    private CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,
                                     boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }



    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor,
                           boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }

        if (isAuthor) {
            Long articleId = article.getId();
            SysUser user = sysUserService.findUserByArticleId(articleId);
            articleVo.setAuthor(sysUserService.copy(user));
        }

        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }

        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(findCategoryById(categoryId));
        }

        return articleVo;
    }

}
