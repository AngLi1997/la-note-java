package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间轴事件查询参数DTO
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 15:30
 */
@Data
@ApiModel(description = "时间轴事件查询参数")
public class TimelineEventQueryDTO {

    @ApiModelProperty("页码，从1开始")
    private Integer pageNum = 1;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = 10;

    @ApiModelProperty("事件分类")
    private String category;
    
    @ApiModelProperty("开始日期 (yyyy-MM-dd)")
    private String startDate;
    
    @ApiModelProperty("结束日期 (yyyy-MM-dd)")
    private String endDate;
} 