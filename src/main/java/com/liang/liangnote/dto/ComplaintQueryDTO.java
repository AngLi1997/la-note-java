package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 拾光查询参数DTO
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
@Data
@ApiModel(description = "拾光查询参数")
public class ComplaintQueryDTO {

    @ApiModelProperty("页码，从1开始")
    private Integer pageNum = 1;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = 10;

    @ApiModelProperty("心情标签")
    private String mood;
} 