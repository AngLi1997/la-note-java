package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.dto.TimelineEventDTO;
import com.liang.liangnote.dto.TimelineEventQueryDTO;
import com.liang.liangnote.service.TimelineEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 时间轴控制器
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 11:00
 */
@RestController
@RequestMapping("/api/timeline")
@Api(tags = "时间轴相关接口")
public class TimelineController {

    @Resource
    private TimelineEventService timelineEventService;

    @GetMapping("/events")
    @ApiOperation("获取时间轴事件列表")
    public Resp<List<TimelineEventDTO>> getTimelineEvents() {
        List<TimelineEventDTO> events = timelineEventService.getAllEvents();
        return Resp.success(events);
    }

    @GetMapping("/categories")
    @ApiOperation("获取时间轴事件分类")
    public Resp<List<String>> getTimelineCategories() {
        List<String> categories = timelineEventService.getAllCategories();
        return Resp.success(categories);
    }
    
    @GetMapping("/list")
    @ApiOperation(value = "分页查询时间轴事件", notes = "支持按分类和日期范围筛选")
    public Resp<PageResponseDTO<TimelineEventDTO>> listTimelineEvents(TimelineEventQueryDTO queryDTO) {
        return timelineEventService.listTimelineEvents(queryDTO);
    }
    
    @GetMapping("/{id}")
    @ApiOperation(value = "获取时间轴事件详情", notes = "根据ID获取事件详情")
    public Resp<TimelineEventDTO> getTimelineEventById(
            @ApiParam(value = "事件ID", required = true)
            @PathVariable String id) {
        return timelineEventService.getTimelineEventById(id);
    }
    
    @PostMapping
    @ApiOperation(value = "创建时间轴事件", notes = "创建新的时间轴事件")
    public Resp<TimelineEventDTO> createTimelineEvent(
            @ApiParam(value = "事件数据", required = true)
            @RequestBody TimelineEventDTO eventDTO) {
        return timelineEventService.createTimelineEvent(eventDTO);
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "更新时间轴事件", notes = "根据ID更新时间轴事件")
    public Resp<TimelineEventDTO> updateTimelineEvent(
            @ApiParam(value = "事件ID", required = true)
            @PathVariable String id,
            @ApiParam(value = "事件数据", required = true)
            @RequestBody TimelineEventDTO eventDTO) {
        // 确保ID一致
        eventDTO.setId(id);
        return timelineEventService.updateTimelineEvent(eventDTO);
    }
    
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除时间轴事件", notes = "根据ID删除时间轴事件")
    public Resp<Boolean> deleteTimelineEvent(
            @ApiParam(value = "事件ID", required = true)
            @PathVariable String id) {
        return timelineEventService.deleteTimelineEvent(id);
    }
} 