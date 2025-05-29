package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.FileUploadDTO;
import com.liang.liangnote.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传控制器
 * @author liang
 * @version 1.0.0
 * @date 2025/5/27 19:10
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
@Api(tags = "文件管理接口")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 上传文件
     * @param file 文件对象
     * @param folderPrefix 文件夹前缀，用于区分业务类型
     * @return 文件上传结果
     */
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Resp<FileUploadDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "文件夹前缀，如：user/avatar/、product/image/", required = false)
            @RequestParam(value = "folderPrefix", required = false) String folderPrefix) {
        log.info("开始上传文件: {}, 前缀: {}", file.getOriginalFilename(), folderPrefix);
        FileUploadDTO result = fileService.uploadFile(file, folderPrefix);
        return Resp.success(result);
    }
} 