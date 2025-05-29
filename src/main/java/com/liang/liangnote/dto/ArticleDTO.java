package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章数据传输对象
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Data
@ApiModel(description = "文章数据传输对象")
public class ArticleDTO {

    @ApiModelProperty("文章ID")
    private String id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("文章分类")
    private String category;

    @ApiModelProperty("文章标签列表")
    private List<String> tags;

    @ApiModelProperty("缩略图URL")
    private String thumbnail;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("浏览量")
    private Integer viewCount;
} 