package com.liang.liangnote.service;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ComplaintDTO;
import com.liang.liangnote.dto.ComplaintQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;

import java.util.List;

/**
 * 吐槽服务接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
public interface ComplaintService {

    /**
     * 分页查询吐槽列表
     *
     * @param queryDTO 查询参数
     * @return 吐槽列表分页结果
     */
    Resp<PageResponseDTO<ComplaintDTO>> listComplaints(ComplaintQueryDTO queryDTO);

    /**
     * 获取所有心情标签
     *
     * @return 心情标签列表
     */
    Resp<List<String>> listMoods();
    
    /**
     * 获取吐槽详情
     *
     * @param id 吐槽ID
     * @return 吐槽详情
     */
    Resp<ComplaintDTO> getComplaintById(String id);
} 