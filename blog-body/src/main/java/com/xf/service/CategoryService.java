package com.xf.service;

import com.xf.vo.Result;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface CategoryService {

    /**
     * 查找所有的类别
     * @return
     */
    Result findAll();

    /**
     * 查找所有类别的详细信息
     * @return
     */
    Result findAllDetail();

    /**
     * 通过类别Id查询
     * @param id
     * @return
     */
    Result findCategoryById(Long id);
}
