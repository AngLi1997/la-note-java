package com.liang.liangnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.liangnote.entity.UserSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户设置Mapper接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Mapper
public interface UserSettingMapper extends BaseMapper<UserSetting> {
} 