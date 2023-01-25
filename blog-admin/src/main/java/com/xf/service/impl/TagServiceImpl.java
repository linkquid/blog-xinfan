package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.mapper.ArticleTagMapper;
import com.xf.dao.mapper.TagMapper;
import com.xf.dao.pojo.ArticleTag;
import com.xf.dao.pojo.Category;
import com.xf.dao.pojo.Tag;
import com.xf.service.ArticleTagService;
import com.xf.service.TagService;
import com.xf.vo.PageResult;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public Result listTags(PageParam pageParam) {
        Page<Tag> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());

        Page<Tag> tagPage = tagMapper.selectPage(page, new LambdaQueryWrapper<>());
        PageResult<Tag> pageResult = new PageResult<>();
        pageResult.setTotal(tagPage.getTotal());
        pageResult.setList(tagPage.getRecords());
        return Result.success(pageResult);
    }

    @Override
    public Result updateTag(Tag tag) {
        tagMapper.updateById(tag);
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result deleteByTagId(Long id) {
        //  1.删除关联表的内容
        articleTagService.deleteByTagId(id);
        //  2.删除标签信息
        tagMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result addTag(Tag tag) {
        tagMapper.insert(tag);
        return Result.success(null);
    }
}
