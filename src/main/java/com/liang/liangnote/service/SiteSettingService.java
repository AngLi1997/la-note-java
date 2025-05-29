package com.liang.liangnote.service;

import com.liang.liangnote.dto.SiteSettingDTO;

/**
 * 网站设置服务接口
 * @author liang
 * @version 1.0.0
 * @date 2023/07/01 10:30
 */
public interface SiteSettingService {
    
    /**
     * 获取网站设置
     * @return 网站设置DTO
     */
    SiteSettingDTO getSiteSetting();
    
    /**
     * 更新网站设置
     * @param siteSettingDTO 网站设置DTO
     * @return 更新后的网站设置DTO
     */
    SiteSettingDTO updateSiteSetting(SiteSettingDTO siteSettingDTO);
} 