package com.liang.liangnote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liang.liangnote.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 拾光实体类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("la_complaint")
public class Complaint extends BaseEntity {

    /**
     * 拾光标题
     */
    private String title;

    /**
     * 拾光内容
     */
    private String content;

    /**
     * 心情标签
     */
    private String mood;

    /**
     * 图片URL，多个以逗号分隔
     */
    private String images;

    /**
     * 发布状态: 0-草稿, 1-已发布
     */
    private Integer status;
} 