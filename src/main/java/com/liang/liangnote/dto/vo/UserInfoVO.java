package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户信息视图对象")
public class UserInfoVO extends BaseVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像URL")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("状态 0-禁用 1-正常")
    private Integer status;

    @ApiModelProperty("角色")
    private String role;
} 