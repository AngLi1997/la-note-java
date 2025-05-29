package com.liang.liangnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.liangnote.entity.SiteSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 网站设置Mapper接口
 * @author liang
 * @version 1.0.0
 * @date 2023/07/01 10:20
 */
@Mapper
public interface SiteSettingMapper extends BaseMapper<SiteSetting> {
} 