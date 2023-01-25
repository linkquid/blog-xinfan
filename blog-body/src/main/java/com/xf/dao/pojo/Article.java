package com.xf.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 文章类
 */
@Data
public class Article {
    public static final int Article_Common = 0;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String summary; //  简介
    private Integer commentCounts;
    private Integer viewCounts;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bodyId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId; //  类别id
    private Integer weight;    //  是否置顶
    private Long createDate;
    private Long updateDate;    //  最后更新时间
}
