package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.SiteSettingDTO;
import com.liang.liangnote.service.SiteSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 网站设置控制器
 * @author liang
 * @version 1.0.0
 * @date 2023/07/01 11:00
 */
@RestController
@RequestMapping("/api/site-settings")
@Api(tags = "网站设置接口")
public class SiteSettingController {
    
    @Resource
    private SiteSettingService siteSettingService;
    
    /**
     * 获取网站设置
     * @return 网站设置
     */
    @GetMapping
    @ApiOperation("获取网站设置")
    public Resp<SiteSettingDTO> getSiteSetting() {
        SiteSettingDTO siteSettingDTO = siteSettingService.getSiteSetting();
        return Resp.success(siteSettingDTO);
    }
    
    /**
     * 更新网站设置
     * @param siteSettingDTO 网站设置DTO
     * @return 更新后的网站设置
     */
    @PutMapping
    @ApiOperation("更新网站设置")
    public Resp<SiteSettingDTO> updateSiteSetting(@RequestBody SiteSettingDTO siteSettingDTO) {
        SiteSettingDTO updatedSetting = siteSettingService.updateSiteSetting(siteSettingDTO);
        return Resp.success(updatedSetting);
    }
} 