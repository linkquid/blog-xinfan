package com.xf.controller;

import com.xf.dao.pojo.Category;
import com.xf.service.CategoryService;
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
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("list")
    public Result listCategory(@RequestBody PageParam pageParam) {
        return categoryService.listCategory(pageParam);
    }

    @PostMapping("update")
    public Result updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping("delete/{id}")
    public Result deleteCategory(@PathVariable("id") Long categoryId) {
        return categoryService.deleteByCategoryId(categoryId);
    }

    @PostMapping("add")
    public Result addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

}
