package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.LoginDTO;
import com.liang.liangnote.dto.LoginResponseDTO;
import com.liang.liangnote.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 认证控制器
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@RestController
@RequestMapping("/api/auth")
@Api(tags = "用户认证接口")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录接口
     *
     * @param loginDTO 登录请求DTO
     * @return 登录响应
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "根据用户名和密码进行登录验证，返回JWT令牌和用户信息")
    public Resp<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
} 