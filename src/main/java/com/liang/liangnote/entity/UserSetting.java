package com.liang.liangnote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liang.liangnote.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置实体类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("la_user_setting")
public class UserSetting extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 个人简介
     */
    private String bio;

    /**
     * 博客介绍
     */
    private String blogIntro;

    /**
     * 邮箱联系方式
     */
    private String contactEmail;

    /**
     * GitHub链接
     */
    private String githubUrl;

    /**
     * 自定义联系方式（JSON格式存储额外的联系方式）
     */
    private String extraContacts;
} 