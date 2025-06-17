package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文件上传响应视图对象")
public class FileUploadVO {
    
    @ApiModelProperty("原始文件名")
    private String originalFilename;
    
    @ApiModelProperty("存储文件名")
    private String filename;
    
    @ApiModelProperty("文件大小(字节)")
    private Long size;
    
    @ApiModelProperty("文件类型")
    private String contentType;
    
    @ApiModelProperty("文件访问URL")
    private String url;
}