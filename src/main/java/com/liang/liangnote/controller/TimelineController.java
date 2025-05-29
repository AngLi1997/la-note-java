package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.TimelineEventDTO;
import com.liang.liangnote.service.TimelineEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
} 