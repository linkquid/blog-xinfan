package com.xf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.dos.Archives;
import com.xf.dao.pojo.Article;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询年度总结
     * @return
     */
    List<Archives> listArchives();

    /**
     * 分页查询文章
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticles(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
