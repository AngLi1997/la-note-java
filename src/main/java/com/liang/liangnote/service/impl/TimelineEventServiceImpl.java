package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.liangnote.dto.TimelineEventDTO;
import com.liang.liangnote.entity.TimelineEvent;
import com.liang.liangnote.mapper.TimelineEventMapper;
import com.liang.liangnote.service.TimelineEventService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 时间轴事件服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 10:50
 */
@Service
public class TimelineEventServiceImpl implements TimelineEventService {

    @Resource
    private TimelineEventMapper timelineEventMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<TimelineEventDTO> getAllEvents() {
        // 查询所有未删除的事件，按事件日期降序排序
        LambdaQueryWrapper<TimelineEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(TimelineEvent::getEventDate);
        queryWrapper.orderByDesc(TimelineEvent::getDisplayOrder);
        
        List<TimelineEvent> eventList = timelineEventMapper.selectList(queryWrapper);
        
        // 转换为DTO
        return eventList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCategories() {
        // 查询所有未删除的事件的分类
        LambdaQueryWrapper<TimelineEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TimelineEvent::getCategory);
        
        List<TimelineEvent> eventList = timelineEventMapper.selectList(queryWrapper);
        
        // 提取分类并去重
        return eventList.stream()
                .map(TimelineEvent::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    
    /**
     * 将实体转换为DTO
     * @param event 时间轴事件实体
     * @return 时间轴事件DTO
     */
    private TimelineEventDTO convertToDTO(TimelineEvent event) {
        if (event == null) {
            return null;
        }
        
        TimelineEventDTO dto = new TimelineEventDTO();
        BeanUtils.copyProperties(event, dto);
        
        // 格式化日期
        if (event.getEventDate() != null) {
            dto.setDate(event.getEventDate().format(DATE_FORMATTER));
        }
        
        return dto;
    }
} 