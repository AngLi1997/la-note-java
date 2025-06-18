package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 文章视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章视图对象")
public class ArticleVO extends BaseVO {

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("文章内容")
    private String content;
    
    @ApiModelProperty("内容预览（当content不返回时使用）")
    private String contentPreview;

    @ApiModelProperty("文章分类")
    private String category;

    @ApiModelProperty("文章标签列表")
    private List<String> tags;

    @ApiModelProperty("缩略图URL")
    private String thumbnail;
    
    @ApiModelProperty("浏览量")
    private Integer viewCount;
    
    @ApiModelProperty("文章状态: 0-草稿, 1-已发布")
    private Integer status;
} 