package com.liang.liangnote.service;

import com.liang.liangnote.dto.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 * @author liang
 * @version 1.0.0
 * @date 2025/5/27 18:45
 */
public interface FileService {
    
    /**
     * 上传文件到MinIO
     * @param file 文件对象
     * @return 文件上传结果
     */
    FileUploadDTO uploadFile(MultipartFile file);
    
    /**
     * 上传文件到MinIO指定文件夹
     * @param file 文件对象
     * @param folderPrefix 文件夹前缀
     * @return 文件上传结果
     */
    FileUploadDTO uploadFile(MultipartFile file, String folderPrefix);
} 