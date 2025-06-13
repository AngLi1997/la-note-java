package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ComplaintDTO;
import com.liang.liangnote.dto.ComplaintQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.entity.Complaint;
import com.liang.liangnote.mapper.ComplaintMapper;
import com.liang.liangnote.service.ComplaintService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拾光服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    @Override
    public Resp<PageResponseDTO<ComplaintDTO>> listComplaints(ComplaintQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Complaint> queryWrapper = Wrappers.lambdaQuery();
        // 只有当不是管理页面查询时，才只查询已发布的拾光
        if (queryDTO.getShowAll() == null || !queryDTO.getShowAll()) {
            queryWrapper.eq(Complaint::getStatus, 1);
        }
        // 按心情标签筛选
        if (StringUtils.isNotBlank(queryDTO.getMood())) {
            queryWrapper.eq(Complaint::getMood, queryDTO.getMood());
        }
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Complaint::getId);

        // 执行分页查询
        Page<Complaint> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Complaint> complaintPage = complaintMapper.selectPage(page, queryWrapper);

        // 转换为DTO
        List<ComplaintDTO> complaintDTOs = complaintPage.getRecords().stream().map(this::convertToDTO).collect(Collectors.toList());

        // 构造分页响应
        PageResponseDTO<ComplaintDTO> pageResponse = PageResponseDTO.of(
                queryDTO.getPageNum(),
                queryDTO.getPageSize(),
                complaintPage.getTotal(),
                complaintDTOs
        );

        return Resp.success(pageResponse);
    }

    @Override
    public Resp<List<String>> listMoods() {
        // 查询所有发布的拾光
        LambdaQueryWrapper<Complaint> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Complaint::getStatus, 1);
        queryWrapper.select(Complaint::getMood);
        List<Complaint> complaints = complaintMapper.selectList(queryWrapper);

        // 提取所有不重复的心情标签
        List<String> moods = complaints.stream()
                .map(Complaint::getMood)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        return Resp.success(moods);
    }
    
    @Override
    public Resp<ComplaintDTO> getComplaintById(String id) {
        if (StringUtils.isBlank(id)) {
            return Resp.failed("拾光ID不能为空");
        }
        
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint == null) {
            return Resp.failed("拾光不存在");
        }
        
        // 转换为DTO
        ComplaintDTO complaintDTO = convertToDTO(complaint);
        
        return Resp.success(complaintDTO);
    }
    
    @Override
    public Resp<ComplaintDTO> createComplaint(ComplaintDTO complaintDTO) {
        if (complaintDTO == null) {
            return Resp.failed("拾光数据不能为空");
        }
        
        // 转换为实体
        Complaint complaint = new Complaint();
        BeanUtils.copyProperties(complaintDTO, complaint);
        
        // 处理图片列表
        if (complaintDTO.getImages() != null && !complaintDTO.getImages().isEmpty()) {
            complaint.setImages(String.join(",", complaintDTO.getImages()));
        } else {
            complaint.setImages(null); // 设置为null而不是空字符串
        }
        
        // 保存到数据库
        complaintMapper.insert(complaint);
        
        // 返回保存后的数据
        return getComplaintById(complaint.getId());
    }
    
    @Override
    public Resp<ComplaintDTO> updateComplaint(ComplaintDTO complaintDTO) {
        if (complaintDTO == null || StringUtils.isBlank(complaintDTO.getId())) {
            return Resp.failed("拾光ID不能为空");
        }
        
        // 检查是否存在
        Complaint existingComplaint = complaintMapper.selectById(complaintDTO.getId());
        if (existingComplaint == null) {
            return Resp.failed("拾光不存在");
        }
        
        // 转换为实体
        Complaint complaint = new Complaint();
        BeanUtils.copyProperties(complaintDTO, complaint);
        
        // 处理图片列表
        if (complaintDTO.getImages() != null && !complaintDTO.getImages().isEmpty()) {
            complaint.setImages(String.join(",", complaintDTO.getImages()));
        } else {
            complaint.setImages(null); // 设置为null而不是空字符串
        }
        
        // 更新到数据库
        complaintMapper.updateById(complaint);
        
        // 返回更新后的数据
        return getComplaintById(complaint.getId());
    }
    
    @Override
    public Resp<Boolean> deleteComplaint(String id) {
        if (StringUtils.isBlank(id)) {
            return Resp.failed("拾光ID不能为空");
        }
        
        // 检查是否存在
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint == null) {
            return Resp.failed("拾光不存在");
        }
        
        // 从数据库删除
        int result = complaintMapper.deleteById(id);
        
        return result > 0 ? Resp.success(true) : Resp.failed("删除失败");
    }

    /**
     * 将拾光实体转换为DTO
     *
     * @param complaint 拾光实体
     * @return 拾光DTO
     */
    private ComplaintDTO convertToDTO(Complaint complaint) {
        ComplaintDTO dto = new ComplaintDTO();
        BeanUtils.copyProperties(complaint, dto);
        
        // 处理图片列表
        if (StringUtils.isNotBlank(complaint.getImages())) {
            List<String> imageList = Arrays.asList(complaint.getImages().split(","));
            dto.setImages(imageList);
        }
        // 不需要else设置空列表，因为DTO中已经初始化为空ArrayList
        
        return dto;
    }
} 