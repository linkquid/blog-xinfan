package com.xf.service;

import com.xf.vo.Result;
import com.xf.vo.params.CommentParam;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface CommentService {

    /**
     * 查找文章中所有评论
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 评论功能
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
