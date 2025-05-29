package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 拾光数据传输对象
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
@Data
@ApiModel(description = "拾光数据传输对象")
public class ComplaintDTO {

    @ApiModelProperty("拾光ID")
    private String id;

    @ApiModelProperty("拾光标题")
    private String title;

    @ApiModelProperty("拾光内容")
    private String content;

    @ApiModelProperty("心情标签")
    private String mood;

    @ApiModelProperty("图片列表")
    private List<String> images;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
} 