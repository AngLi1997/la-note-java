package com.liang.liangnote.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.liangnote.dto.SiteSettingDTO;
import com.liang.liangnote.entity.SiteSetting;
import com.liang.liangnote.mapper.SiteSettingMapper;
import com.liang.liangnote.service.SiteSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 网站设置服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2023/07/01 10:40
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {
    
    @Resource
    private SiteSettingMapper siteSettingMapper;
    
    @Override
    public SiteSettingDTO getSiteSetting() {
        // 查询默认网站设置
        LambdaQueryWrapper<SiteSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SiteSetting::getIsDefault, true);
        SiteSetting siteSetting = siteSettingMapper.selectOne(queryWrapper);
        
        // 如果没有默认设置，则查询第一条记录
        if (siteSetting == null) {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByAsc(SiteSetting::getCreateTime);
            queryWrapper.last("LIMIT 1");
            siteSetting = siteSettingMapper.selectOne(queryWrapper);
        }
        
        // 如果仍然为空，则返回空DTO
        if (siteSetting == null) {
            return new SiteSettingDTO();
        }
        
        // 转换为DTO
        return convertToDTO(siteSetting);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SiteSettingDTO updateSiteSetting(SiteSettingDTO siteSettingDTO) {
        // 查询是否存在
        SiteSetting siteSetting;
        if (StringUtils.hasText(siteSettingDTO.getId())) {
            siteSetting = siteSettingMapper.selectById(siteSettingDTO.getId());
        } else {
            // 查询默认设置
            LambdaQueryWrapper<SiteSetting> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SiteSetting::getIsDefault, true);
            siteSetting = siteSettingMapper.selectOne(queryWrapper);
            
            // 如果不存在默认设置，则创建新的
            if (siteSetting == null) {
                siteSetting = new SiteSetting();
                siteSetting.setIsDefault(true);
            }
        }
        
        // 更新属性
        siteSetting.setTitle(siteSettingDTO.getTitle());
        siteSetting.setSubtitle(siteSettingDTO.getSubtitle());
        siteSetting.setDescription(siteSettingDTO.getDescription());
        siteSetting.setSlogan(siteSettingDTO.getSlogan());
        siteSetting.setAvatar(siteSettingDTO.getAvatar());
        siteSetting.setKeywords(siteSettingDTO.getKeywords());
        siteSetting.setIcp(siteSettingDTO.getIcp());
        
        // 处理社交链接
        if (siteSettingDTO.getSocialLinks() != null) {
            siteSetting.setSocialLinks(JSON.toJSONString(siteSettingDTO.getSocialLinks()));
        }
        
        // 保存或更新
        if (siteSetting.getId() != null) {
            siteSettingMapper.updateById(siteSetting);
        } else {
            siteSettingMapper.insert(siteSetting);
        }
        
        // 返回更新后的DTO
        return convertToDTO(siteSetting);
    }
    
    /**
     * 将实体转换为DTO
     * @param siteSetting 网站设置实体
     * @return 网站设置DTO
     */
    private SiteSettingDTO convertToDTO(SiteSetting siteSetting) {
        SiteSettingDTO dto = new SiteSettingDTO();
        BeanUtils.copyProperties(siteSetting, dto);
        
        // 处理社交链接
        if (StringUtils.hasText(siteSetting.getSocialLinks())) {
            try {
                List<String> socialLinks = JSON.parseArray(siteSetting.getSocialLinks(), String.class);
                dto.setSocialLinks(socialLinks);
            } catch (Exception e) {
                // 解析失败则设置为空列表
                dto.setSocialLinks(new ArrayList<>());
            }
        } else {
            dto.setSocialLinks(new ArrayList<>());
        }
        
        return dto;
    }
} 