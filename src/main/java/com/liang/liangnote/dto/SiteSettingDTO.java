package com.liang.liangnote.dto;

import lombok.Data;

import java.util.List;

/**
 * 网站设置数据传输对象
 * @author liang
 * @version 1.0.0
 * @date 2023/07/01 10:10
 */
@Data
public class SiteSettingDTO {
    
    /**
     * ID
     */
    private String id;
    
    /**
     * 网站标题
     */
    private String title;
    
    /**
     * 网站副标题
     */
    private String subtitle;
    
    /**
     * 网站描述
     */
    private String description;
    
    /**
     * 网站标语/口号
     */
    private String slogan;
    
    /**
     * 网站头像/Logo
     */
    private String avatar;
    
    /**
     * 社交链接列表
     */
    private List<String> socialLinks;
    
    /**
     * 网站关键词
     */
    private String keywords;
    
    /**
     * 备案信息
     */
    private String icp;
} 