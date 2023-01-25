package com.xf.vo.params;

import lombok.Data;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : 页面参数
 */
@Data
public class PageParam {

    private int page = 1;

    private int PageSize = 10;

    private Long CategoryId;

    private Long TagId;

    private String year;
    private String month;

    public String getMonth() {
        if (this.month != null && this.month.length() == 1) {
            return "0" + this.month;
        }
        return this.month;
    }
}
