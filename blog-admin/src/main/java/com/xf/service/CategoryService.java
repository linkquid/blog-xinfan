package com.xf.service;

import com.xf.dao.pojo.Category;
import com.xf.vo.Result;
import com.xf.vo.params.PageParam;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface CategoryService {
    /**
     * 分页查询类别信息
     * @param pageParam
     * @return
     */
    Result listCategory(PageParam pageParam);

    /**
     * 更新标签信息
     * @param category
     * @return
     */
    Result updateCategory(Category category);

    /**
     * 通过CategoryId删除类别
     * @param categoryId
     * @return
     */
    Result deleteByCategoryId(Long categoryId);

    /**
     * 添加类别
     * @param category
     * @return
     */
    Result addCategory(Category category);
}
