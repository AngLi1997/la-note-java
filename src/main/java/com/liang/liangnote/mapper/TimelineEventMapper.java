package com.liang.liangnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.liangnote.entity.TimelineEvent;
import org.apache.ibatis.annotations.Mapper;

/**
 * 时间轴事件Mapper
 * @author liang
 * @version 1.0.0
 * @date 2025/5/30 10:40
 */
@Mapper
public interface TimelineEventMapper extends BaseMapper<TimelineEvent> {
} 