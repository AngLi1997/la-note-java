package com.liang.liangnote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liang.liangnote.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网站设置实体类
 * @author liang
 * @version 1.0.0
 * @date 2023/07/01 10:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("la_site_setting")
public class SiteSetting extends BaseEntity {
    
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
     * 社交链接，JSON格式存储
     */
    private String socialLinks;
    
    /**
     * 网站关键词
     */
    private String keywords;
    
    /**
     * 备案信息
     */
    private String icp;
    
    /**
     * 是否为默认设置
     */
    private Boolean isDefault;
} 