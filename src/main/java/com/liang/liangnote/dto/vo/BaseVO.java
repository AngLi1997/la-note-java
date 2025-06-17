package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:00
 */
@Data
public class BaseVO {
    
    @ApiModelProperty("ID")
    private String id;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
} 