package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录响应视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:35
 */
@Data
@ApiModel(description = "登录响应视图对象")
public class LoginResponseVO {

    @ApiModelProperty("JWT令牌")
    private String token;

    @ApiModelProperty("用户信息")
    private UserInfoVO userInfo;
} 