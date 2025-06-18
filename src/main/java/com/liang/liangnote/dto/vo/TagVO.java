package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签视图对象
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "标签视图对象")
public class TagVO {

    @ApiModelProperty("标签名称")
    private String name;

    @ApiModelProperty("文章数量")
    private Integer count;
} 