package com.xf.dao.dos;

import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 年度总结
 */
@Data
public class Archives {

    private Integer year;
    private Integer month;
    private Long count;

}
