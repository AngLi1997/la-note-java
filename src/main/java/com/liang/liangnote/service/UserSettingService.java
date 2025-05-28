package com.liang.liangnote.service;

import com.liang.liangnote.dto.UserSettingDTO;
import com.liang.liangnote.entity.UserSetting;

/**
 * 用户设置服务接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
public interface UserSettingService {
    
    /**
     * 根据用户ID获取用户设置
     * @param userId 用户ID
     * @return 用户设置
     */
    UserSetting getByUserId(String userId);
    
    /**
     * 保存或更新用户设置
     * @param userSetting 用户设置
     * @return 是否成功
     */
    boolean saveOrUpdateSetting(UserSetting userSetting);
    
    /**
     * 获取用户设置信息(包含用户基本信息)
     * @param userId 用户ID
     * @return 用户设置DTO
     */
    UserSettingDTO getUserSettingInfo(String userId);
} 