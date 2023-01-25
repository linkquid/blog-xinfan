package com.xf.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Data
public class CommentParam {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;
    private String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long Parent;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUserId;

}
