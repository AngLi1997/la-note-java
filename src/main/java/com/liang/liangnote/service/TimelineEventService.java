package com.liang.liangnote.service;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.dto.TimelineEventDTO;
import com.liang.liangnote.dto.TimelineEventQueryDTO;

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
    
    /**
     * 分页查询时间轴事件
     * 
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Resp<PageResponseDTO<TimelineEventDTO>> listTimelineEvents(TimelineEventQueryDTO queryDTO);
    
    /**
     * 获取时间轴事件详情
     * 
     * @param id 事件ID
     * @return 事件详情
     */
    Resp<TimelineEventDTO> getTimelineEventById(String id);
    
    /**
     * 创建时间轴事件
     * 
     * @param eventDTO 事件数据
     * @return 创建结果
     */
    Resp<TimelineEventDTO> createTimelineEvent(TimelineEventDTO eventDTO);
    
    /**
     * 更新时间轴事件
     * 
     * @param eventDTO 事件数据
     * @return 更新结果
     */
    Resp<TimelineEventDTO> updateTimelineEvent(TimelineEventDTO eventDTO);
    
    /**
     * 删除时间轴事件
     * 
     * @param id 事件ID
     * @return 删除结果
     */
    Resp<Boolean> deleteTimelineEvent(String id);
} 