package com.xf.controller;

import com.xf.dao.pojo.Tag;
import com.xf.service.TagService;
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
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("list")
    public Result listTags(@RequestBody PageParam pageParam) {
        return tagService.listTags(pageParam);
    }

    @PostMapping("update")
    public Result updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    @GetMapping("delete/{id}")
    public Result deleteTag(@PathVariable("id") Long id) {
        return tagService.deleteByTagId(id);
    }

    @PostMapping("add")
    public Result addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

}
