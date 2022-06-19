package com.ycourlee.tranquil.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yooonn
 * @date 2020.09.29
 */
@ApiModel
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 3985752812408085575L;
    @ApiModelProperty("页码")
    private int     page;
    @ApiModelProperty("页大小")
    private int     pageSize;
    @ApiModelProperty("总条数")
    private long    total;
    @ApiModelProperty("总页数")
    private int     totalPages;
    @ApiModelProperty("数据")
    private List<T> list = new ArrayList<>();

    public PageResponse() {}

    public PageResponse(List<T> list, long total, int page, int pageSize) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = ((int) (pageSize <= 0 ? 0 : (total % pageSize == 0) ? total / pageSize : total / pageSize + 1));
    }

    public PageResponse(List<T> list, long total, PageRequest pageRequest) {
        this.list = list;
        this.total = total;
        this.page = pageRequest.getPage();
        this.pageSize = pageRequest.getPageSize();
        this.totalPages = ((int) (pageSize <= 0 ? 0 : (total % pageSize == 0) ? total / pageSize : total / pageSize + 1));
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getList() {
        return list;
    }
}
