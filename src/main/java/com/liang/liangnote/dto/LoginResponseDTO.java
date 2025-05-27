package com.liang.liangnote.dto;

import lombok.Data;

/**
 * 登录响应DTO
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Data
public class LoginResponseDTO {

    /**
     * JWT令牌
     */
    private String token;

    /**
     * 用户信息
     */
    private UserInfoDTO userInfo;
} 