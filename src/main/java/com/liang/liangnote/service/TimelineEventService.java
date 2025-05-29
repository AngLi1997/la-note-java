package com.liang.liangnote.service;

import com.liang.liangnote.dto.TimelineEventDTO;

import java.util.List;

/**
 * 时间轴事件服务接口
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 10:45
 */
public interface TimelineEventService {

    /**
     * 获取所有时间轴事件
     * @return 时间轴事件列表
     */
    List<TimelineEventDTO> getAllEvents();

    /**
     * 获取所有时间轴事件分类
     * @return 分类列表
     */
    List<String> getAllCategories();
} 