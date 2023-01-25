package com.xf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 评论传输类
 */
@Data
public class CommentVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String content; //  评论内容
    private String createDate;  //  评论时间
    private UserVo author;  //  评论人
    private List<CommentVo> childrens;   //  子评论
    private UserVo toUser;  //  父评论
    private Integer level;  //  一级评论？

}
