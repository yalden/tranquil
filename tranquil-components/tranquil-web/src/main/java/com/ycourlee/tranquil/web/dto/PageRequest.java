package com.ycourlee.tranquil.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yooonnjiang
 * @date 2020.09.29
 */
@ApiModel
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -5858020162072499804L;

    @ApiModelProperty("页码")
    private Integer page     = 1;
    @ApiModelProperty("页大小")
    private Integer pageSize = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
