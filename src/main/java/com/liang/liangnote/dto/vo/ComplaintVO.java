package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 拾光视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "拾光视图对象")
public class ComplaintVO extends BaseVO {

    @ApiModelProperty("拾光标题")
    private String title;

    @ApiModelProperty("拾光内容")
    private String content;

    @ApiModelProperty("心情标签")
    private String mood;

    @ApiModelProperty("图片列表")
    private List<String> images = new ArrayList<>();
    
    @ApiModelProperty("状态 0-草稿 1-已发布")
    private Integer status;
} 