package com.ycourlee.tranquil.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yooonn
 * @date 2020.09.29
 */
@ApiModel(parent = PageRequest.class)
public class AdvancePageRequest extends PageRequest {

    private static final long serialVersionUID = 359409250687293869L;

    @ApiModelProperty("排序标志")
    private String orderBy;
    /**
     * ascend or descend
     */
    @ApiModelProperty("排序方式")
    private String order;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
