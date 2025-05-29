package com.liang.liangnote.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liang.liangnote.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 时间轴事件实体
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("la_timeline_event")
public class TimelineEvent extends BaseEntity {

    /**
     * 事件标题
     */
    private String title;

    /**
     * 事件内容
     */
    private String content;

    /**
     * 事件日期
     */
    private LocalDate eventDate;

    /**
     * 事件分类
     */
    private String category;

    /**
     * 事件图标
     */
    private String icon;

    /**
     * 显示顺序
     */
    private Integer displayOrder;
} 