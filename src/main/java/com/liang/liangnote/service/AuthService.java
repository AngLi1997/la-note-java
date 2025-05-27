package com.liang.liangnote.service;

import com.liang.liangnote.dto.LoginDTO;
import com.liang.liangnote.dto.LoginResponseDTO;
import com.liang.liangnote.common.Resp;

/**
 * 认证服务接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录请求DTO
     * @return 登录响应
     */
    Resp<LoginResponseDTO> login(LoginDTO loginDTO);
} 