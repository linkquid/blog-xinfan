package com.xf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xf.dao.mapper.ArticleMapper;
import com.xf.dao.mapper.CommentMapper;
import com.xf.dao.pojo.Article;
import com.xf.dao.pojo.Comment;
import com.xf.dao.pojo.SysUser;
import com.xf.service.CommentService;
import com.xf.service.SysUserService;
import com.xf.utils.UserThreadLocal;
import com.xf.vo.CommentVo;
import com.xf.vo.Result;
import com.xf.vo.UserVo;
import com.xf.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        //  1.通过文章Id 查询所有的评论列表
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);
        queryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos = copyList(comments);
        //  2.通过作者Id 查询评论人的信息

        //  3.判断level=1 有无盖楼
        //  4.根据评论Id 查询楼中楼评论
        return Result.success(commentVos);
    }


    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        queryWrapper.orderByAsc(Comment::getCreateDate);

        return copyList(commentMapper.selectList(queryWrapper));

    }

    @Override
    public Result comment(CommentParam commentParam) {
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        SysUser sysUser = UserThreadLocal.get();
        comment.setAuthorId(sysUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (null == parent || 0 == parent) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        Article article = articleMapper.selectById(comment.getArticleId());
        article.setCommentCounts(article.getCommentCounts() + 1);
        articleMapper.updateById(article);

        CommentVo commentVo = copy(comment);
        return Result.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);

        //  1.作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoByAuthorId(authorId);
        commentVo.setAuthor(userVo);

        //  2.子评论
        Integer level = comment.getLevel();
        if (1 == level) {
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }

        //  3.父评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoByAuthorId(toUid);
            commentVo.setToUser(toUserVo);
        }

        //  4.评论时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        commentVo.setCreateDate(format.format(new Date(comment.getCreateDate())));

        return commentVo;
    }

}
