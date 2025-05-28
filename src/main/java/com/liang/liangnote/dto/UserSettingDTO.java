package com.liang.liangnote.dto;

import lombok.Data;

/**
 * 用户设置DTO
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Data
public class UserSettingDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

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