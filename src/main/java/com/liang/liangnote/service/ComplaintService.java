package com.liang.liangnote.service;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ComplaintDTO;
import com.liang.liangnote.dto.ComplaintQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;

import java.util.List;

/**
 * 拾光服务接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
public interface ComplaintService {

    /**
     * 分页查询拾光列表
     *
     * @param queryDTO 查询参数
     * @return 拾光列表分页结果
     */
    Resp<PageResponseDTO<ComplaintDTO>> listComplaints(ComplaintQueryDTO queryDTO);

    /**
     * 获取所有心情标签
     *
     * @return 心情标签列表
     */
    Resp<List<String>> listMoods();
    
    /**
     * 获取拾光详情
     *
     * @param id 拾光ID
     * @return 拾光详情
     */
    Resp<ComplaintDTO> getComplaintById(String id);
} 