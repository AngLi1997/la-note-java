package com.liang.liangnote.service.impl;

import com.liang.liangnote.config.MinioConfig;
import com.liang.liangnote.dto.FileUploadDTO;
import com.liang.liangnote.exception.BusinessException;
import com.liang.liangnote.service.FileService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

/**
 * 文件服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2025/5/27 18:50
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private MinioClient minioClient;
    
    @Resource
    private MinioConfig minioConfig;

    @Override
    public FileUploadDTO uploadFile(MultipartFile file) {
        return uploadFile(file, null);
    }
    
    @Override
    public FileUploadDTO uploadFile(MultipartFile file, String folderPrefix) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        
        try {
            // 获取原始文件名和文件类型
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            
            // 生成新文件名：时间戳 + UUID，防止文件冲突
            String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replaceAll("-", "");
            
            // 保留原始文件扩展名
            if (originalFilename != null && originalFilename.contains(".")) {
                fileName += originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            // 处理文件夹前缀
            String objectName = fileName;
            if (StringUtils.isNotBlank(folderPrefix)) {
                // 确保前缀以/结尾，不以/开头
                if (folderPrefix.startsWith("/")) {
                    folderPrefix = folderPrefix.substring(1);
                }
                if (!folderPrefix.endsWith("/")) {
                    folderPrefix = folderPrefix + "/";
                }
                objectName = folderPrefix + fileName;
            }
            
            // 检查存储桶是否存在，不存在则创建
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .build());
            
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build());
            }
            
            // 上传文件
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectName)
                    .contentType(contentType)
                    .stream(inputStream, file.getSize(), -1)
                    .build());
            
            inputStream.close();
            
            // 构建文件访问URL
            String url = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName;
            
            // 构建并返回结果
            return new FileUploadDTO(originalFilename, objectName, file.getSize(), contentType, url);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }
} 