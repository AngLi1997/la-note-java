package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间轴事件数据传输对象
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 10:35
 */
@Data
@ApiModel(description = "时间轴事件DTO")
public class TimelineEventDTO {

    @ApiModelProperty("事件ID")
    private String id;

    @ApiModelProperty("事件标题")
    private String title;

    @ApiModelProperty("事件内容")
    private String content;

    @ApiModelProperty("事件日期")
    private String date;

    @ApiModelProperty("事件分类")
    private String category;

    @ApiModelProperty("事件图标")
    private String icon;
} 