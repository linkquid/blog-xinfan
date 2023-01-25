package com.xf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xf.dao.pojo.Tag;
import com.xf.vo.TagVo;

import java.util.List;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce :
 */
public interface TagMapper extends BaseMapper<Tag> {


    /**
     * 通过 文章Id 查询所有标签
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热门的标签
     * @param limit
     * @return
     */
    List<Tag> findHotTags(int limit);

}
