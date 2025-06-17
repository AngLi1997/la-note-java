package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户设置视图对象")
public class UserSettingVO extends BaseVO {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像URL")
    private String avatar;

    @ApiModelProperty("个人简介")
    private String bio;

    @ApiModelProperty("博客介绍")
    private String blogIntro;

    @ApiModelProperty("邮箱联系方式")
    private String contactEmail;

    @ApiModelProperty("GitHub链接")
    private String githubUrl;

    @ApiModelProperty("自定义联系方式（JSON格式存储额外的联系方式）")
    private String extraContacts;
} 