package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文章查询参数DTO
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Data
@ApiModel(description = "文章查询参数")
public class ArticleQueryDTO {

    @ApiModelProperty("页码，从1开始")
    private Integer pageNum = 1;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = 10;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("标签")
    private String tag;
} 