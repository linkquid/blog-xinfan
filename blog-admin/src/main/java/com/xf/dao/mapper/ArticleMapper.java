package com.xf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xf.dao.pojo.Article;
import org.apache.ibatis.annotations.Select;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("update ms_article set state = 1 where id = #{articleId}")
    void agreeArticleById(Long articleId);

    @Select("update ms_article set state = 2 where id = #{articleId}")
    void disagreeArticleById(Long articleId);
}
