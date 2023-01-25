package com.xf.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xf.dao.pojo.Category;
import com.xf.vo.CategoryVo;
import com.xf.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 文章参数
 */
@Data
public class ArticleParam {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private ArticleBodyParam body;
    private Category category;
    private String summary;
    private List<TagVo> tags;
    private String title;
    private String search;
}
