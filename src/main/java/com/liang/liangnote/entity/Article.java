package com.liang.liangnote.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liang.liangnote.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章实体类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("la_article")
public class Article extends BaseEntity {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章分类
     */
    private String category;

    /**
     * 文章标签，以逗号分隔
     */
    private String tags;

    /**
     * 缩略图URL
     */
    private String thumbnail;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 发布状态: 0-草稿, 1-已发布
     */
    private Integer status;
} 