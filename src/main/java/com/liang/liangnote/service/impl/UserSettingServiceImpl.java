package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.liangnote.dto.UserSettingDTO;
import com.liang.liangnote.entity.User;
import com.liang.liangnote.entity.UserSetting;
import com.liang.liangnote.mapper.UserMapper;
import com.liang.liangnote.mapper.UserSettingMapper;
import com.liang.liangnote.service.UserSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户设置服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Service
public class UserSettingServiceImpl implements UserSettingService {

    @Resource
    private UserSettingMapper userSettingMapper;
    
    @Resource
    private UserMapper userMapper;

    @Override
    public UserSetting getByUserId(String userId) {
        LambdaQueryWrapper<UserSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSetting::getUserId, userId);
        return userSettingMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean saveOrUpdateSetting(UserSetting userSetting) {
        // 查询是否存在设置
        UserSetting existSetting = getByUserId(userSetting.getUserId());
        
        if (existSetting != null) {
            // 设置ID，进行更新
            userSetting.setId(existSetting.getId());
            return userSettingMapper.updateById(userSetting) > 0;
        } else {
            // 新增设置
            return userSettingMapper.insert(userSetting) > 0;
        }
    }

    @Override
    public UserSettingDTO getUserSettingInfo(String userId) {
        // 查询用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        
        // 查询用户设置
        UserSetting userSetting = getByUserId(userId);
        
        // 组装返回数据
        UserSettingDTO dto = new UserSettingDTO();
        dto.setUserId(userId);
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setAvatar(user.getAvatar());
        
        // 设置设置信息
        if (userSetting != null) {
            dto.setBio(userSetting.getBio());
            dto.setBlogIntro(userSetting.getBlogIntro());
            dto.setContactEmail(userSetting.getContactEmail());
            dto.setGithubUrl(userSetting.getGithubUrl());
            dto.setExtraContacts(userSetting.getExtraContacts());
        }
        
        return dto;
    }
} 