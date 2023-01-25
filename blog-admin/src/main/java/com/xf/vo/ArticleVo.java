package com.xf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
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
    private Integer weight;    //  是否置顶
    private String createDate;
    private String updateDate;
}
