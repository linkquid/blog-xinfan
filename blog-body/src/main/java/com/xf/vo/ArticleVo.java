package com.xf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xf.dao.pojo.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 文章信息
 */

@Data
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String summary; //  简介
    private Integer commentCounts;
    private Integer viewCounts;
    private UserVo author;
    private ArticleBodyVo body;
    private CategoryVo category; //  类别id
    private List<TagVo> tags;
    private Integer weight;    //  是否置顶
    private String createDate;

}
