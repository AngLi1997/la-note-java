package com.liang.liangnote.dto;

import lombok.Data;

/**
 * 用户信息DTO
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Data
public class UserInfoDTO {

    /**
     * 用户ID
     */
    private String id;

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
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 角色
     */
    private String role;
} 