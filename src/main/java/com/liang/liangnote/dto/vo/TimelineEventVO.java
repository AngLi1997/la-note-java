package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 时间轴事件视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "时间轴事件视图对象")
public class TimelineEventVO extends BaseVO {

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
    
    @ApiModelProperty("显示顺序")
    private Integer displayOrder;
} 