package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.dto.TimelineEventDTO;
import com.liang.liangnote.dto.TimelineEventQueryDTO;
import com.liang.liangnote.entity.TimelineEvent;
import com.liang.liangnote.mapper.TimelineEventMapper;
import com.liang.liangnote.service.TimelineEventService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
    }
    
    @Override
    public Resp<PageResponseDTO<TimelineEventDTO>> listTimelineEvents(TimelineEventQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<TimelineEvent> queryWrapper = Wrappers.lambdaQuery();
        
        // 按分类筛选
        if (StringUtils.isNotBlank(queryDTO.getCategory())) {
            queryWrapper.eq(TimelineEvent::getCategory, queryDTO.getCategory());
        }
        
        // 按日期范围筛选
        if (StringUtils.isNotBlank(queryDTO.getStartDate())) {
            try {
                LocalDate startDate = LocalDate.parse(queryDTO.getStartDate(), DATE_FORMATTER);
                queryWrapper.ge(TimelineEvent::getEventDate, startDate);
            } catch (DateTimeParseException e) {
                return Resp.failed("开始日期格式不正确，应为yyyy-MM-dd");
            }
        }
        
        if (StringUtils.isNotBlank(queryDTO.getEndDate())) {
            try {
                LocalDate endDate = LocalDate.parse(queryDTO.getEndDate(), DATE_FORMATTER);
                queryWrapper.le(TimelineEvent::getEventDate, endDate);
            } catch (DateTimeParseException e) {
                return Resp.failed("结束日期格式不正确，应为yyyy-MM-dd");
            }
        }
        
        // 按事件日期降序排序，然后按显示顺序排序
        queryWrapper.orderByDesc(TimelineEvent::getEventDate);
        queryWrapper.orderByAsc(TimelineEvent::getDisplayOrder);
        
        // 执行分页查询
        Page<TimelineEvent> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<TimelineEvent> eventPage = timelineEventMapper.selectPage(page, queryWrapper);
        
        // 转换为DTO
        List<TimelineEventDTO> eventDTOs = eventPage.getRecords().stream().map(this::convertToDTO).collect(Collectors.toList());
        
        // 构造分页响应
        PageResponseDTO<TimelineEventDTO> pageResponse = PageResponseDTO.of(
                queryDTO.getPageNum(),
                queryDTO.getPageSize(),
                eventPage.getTotal(),
                eventDTOs
        );
        
        return Resp.success(pageResponse);
    }
    
    @Override
    public Resp<TimelineEventDTO> getTimelineEventById(String id) {
        if (StringUtils.isBlank(id)) {
            return Resp.failed("事件ID不能为空");
        }
        
        TimelineEvent event = timelineEventMapper.selectById(id);
        if (event == null) {
            return Resp.failed("事件不存在");
        }
        
        // 转换为DTO
        TimelineEventDTO eventDTO = convertToDTO(event);
        
        return Resp.success(eventDTO);
    }
    
    @Override
    public Resp<TimelineEventDTO> createTimelineEvent(TimelineEventDTO eventDTO) {
        if (eventDTO == null) {
            return Resp.failed("事件数据不能为空");
        }
        
        if (StringUtils.isBlank(eventDTO.getTitle())) {
            return Resp.failed("事件标题不能为空");
        }
        
        if (StringUtils.isBlank(eventDTO.getDate())) {
            return Resp.failed("事件日期不能为空");
        }
        
        // 转换为实体
        TimelineEvent event = new TimelineEvent();
        
        try {
            // 解析日期
            LocalDate eventDate = LocalDate.parse(eventDTO.getDate(), DATE_FORMATTER);
            event.setEventDate(eventDate);
        } catch (DateTimeParseException e) {
            return Resp.failed("日期格式不正确，应为yyyy-MM-dd");
        }
        
        // 设置其他字段
        event.setTitle(eventDTO.getTitle());
        event.setContent(eventDTO.getContent());
        event.setCategory(eventDTO.getCategory());
        event.setIcon(eventDTO.getIcon());
        
        // 如果没有设置显示顺序，默认为0
        if (eventDTO.getDisplayOrder() != null) {
            event.setDisplayOrder(eventDTO.getDisplayOrder());
        } else {
            event.setDisplayOrder(0);
        }
        
        // 保存到数据库
        timelineEventMapper.insert(event);
        
        // 返回保存后的数据
        return getTimelineEventById(event.getId());
    }
    
    @Override
    public Resp<TimelineEventDTO> updateTimelineEvent(TimelineEventDTO eventDTO) {
        if (eventDTO == null || StringUtils.isBlank(eventDTO.getId())) {
            return Resp.failed("事件ID不能为空");
        }
        
        // 检查是否存在
        TimelineEvent existingEvent = timelineEventMapper.selectById(eventDTO.getId());
        if (existingEvent == null) {
            return Resp.failed("事件不存在");
        }
        
        // 转换为实体
        TimelineEvent event = new TimelineEvent();
        event.setId(eventDTO.getId());
        
        if (StringUtils.isNotBlank(eventDTO.getTitle())) {
            event.setTitle(eventDTO.getTitle());
        }
        
        if (StringUtils.isNotBlank(eventDTO.getContent())) {
            event.setContent(eventDTO.getContent());
        }
        
        if (StringUtils.isNotBlank(eventDTO.getDate())) {
            try {
                // 解析日期
                LocalDate eventDate = LocalDate.parse(eventDTO.getDate(), DATE_FORMATTER);
                event.setEventDate(eventDate);
            } catch (DateTimeParseException e) {
                return Resp.failed("日期格式不正确，应为yyyy-MM-dd");
            }
        }
        
        if (StringUtils.isNotBlank(eventDTO.getCategory())) {
            event.setCategory(eventDTO.getCategory());
        }
        
        if (StringUtils.isNotBlank(eventDTO.getIcon())) {
            event.setIcon(eventDTO.getIcon());
        }
        
        if (eventDTO.getDisplayOrder() != null) {
            event.setDisplayOrder(eventDTO.getDisplayOrder());
        }
        
        // 更新到数据库
        timelineEventMapper.updateById(event);
        
        // 返回更新后的数据
        return getTimelineEventById(event.getId());
    }
    
    @Override
    public Resp<Boolean> deleteTimelineEvent(String id) {
        if (StringUtils.isBlank(id)) {
            return Resp.failed("事件ID不能为空");
        }
        
        // 检查是否存在
        TimelineEvent event = timelineEventMapper.selectById(id);
        if (event == null) {
            return Resp.failed("事件不存在");
        }
        
        // 从数据库删除
        int result = timelineEventMapper.deleteById(id);
        
        return result > 0 ? Resp.success(true) : Resp.failed("删除失败");
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
        
        // 设置显示顺序
        dto.setDisplayOrder(event.getDisplayOrder());
        
        return dto;
    }
} 