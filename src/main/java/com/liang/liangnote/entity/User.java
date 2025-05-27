package com.liang.liangnote.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liang.liangnote.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("la_user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

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
     * 用户角色，不对应数据库字段
     */
    @TableField(exist = false)
    private String role;
} 