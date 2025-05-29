package com.liang.liangnote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应DTO
 * @author liang
 * @version 1.0.0
 * @date 2025/5/27 18:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO {
    
    /**
     * 原始文件名
     */
    private String originalFilename;
    
    /**
     * 存储文件名
     */
    private String filename;
    
    /**
     * 文件大小(字节)
     */
    private Long size;
    
    /**
     * 文件类型
     */
    private String contentType;
    
    /**
     * 文件访问URL
     */
    private String url;
} 