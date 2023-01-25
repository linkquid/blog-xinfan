package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xf.dao.mapper.CategoryMapper;
import com.xf.dao.pojo.Category;
import com.xf.dao.pojo.Permission;
import com.xf.service.CategoryService;
import com.xf.vo.PageResult;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result listCategory(PageParam pageParam) {
        Page<Category> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());

        Page<Category> categoryPage = categoryMapper.selectPage(page, new LambdaQueryWrapper<>());
        PageResult<Category> pageResult = new PageResult<>();
        pageResult.setTotal(categoryPage.getTotal());
        pageResult.setList(categoryPage.getRecords());
        return Result.success(pageResult);
    }

    @Override
    public Result updateCategory(Category category) {
        categoryMapper.updateById(category);
        return Result.success(null);
    }

    /**
     * 这样删除的话，文章引用的CategoryId指向该表为空了，有待优化。
     * @param categoryId
     * @return
     */
    @Override
    public Result deleteByCategoryId(Long categoryId) {
        categoryMapper.deleteById(categoryId);
        return Result.success(null);
    }

    @Override
    public Result addCategory(Category category) {
        categoryMapper.insert(category);
        return Result.success(null);
    }
}
