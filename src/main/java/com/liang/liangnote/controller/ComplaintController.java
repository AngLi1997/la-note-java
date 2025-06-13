package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ComplaintDTO;
import com.liang.liangnote.dto.ComplaintQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.service.ComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 拾光控制器
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
@RestController
@RequestMapping("/api/complaints")
@Api(tags = "拾光接口")
public class ComplaintController {

    @Resource
    private ComplaintService complaintService;

    /**
     * 分页查询拾光列表
     *
     * @param queryDTO 查询参数
     * @return 拾光分页列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询拾光列表", notes = "支持按心情筛选")
    public Resp<PageResponseDTO<ComplaintDTO>> listComplaints(ComplaintQueryDTO queryDTO) {
        return complaintService.listComplaints(queryDTO);
    }

    /**
     * 获取所有心情标签
     *
     * @return 心情标签列表
     */
    @GetMapping("/moods")
    @ApiOperation(value = "获取所有心情标签", notes = "返回所有心情标签列表")
    public Resp<List<String>> listMoods() {
        return complaintService.listMoods();
    }
    
    /**
     * 获取拾光详情
     *
     * @param id 拾光ID
     * @return 拾光详情
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取拾光详情", notes = "根据ID获取拾光详情")
    public Resp<ComplaintDTO> getComplaintById(
            @ApiParam(value = "拾光ID", required = true)
            @PathVariable String id) {
        return complaintService.getComplaintById(id);
    }
    
    /**
     * 创建拾光
     *
     * @param complaintDTO 拾光数据
     * @return 创建结果
     */
    @PostMapping
    @ApiOperation(value = "创建拾光", notes = "创建新的拾光")
    public Resp<ComplaintDTO> createComplaint(
            @ApiParam(value = "拾光数据", required = true)
            @RequestBody ComplaintDTO complaintDTO) {
        return complaintService.createComplaint(complaintDTO);
    }
    
    /**
     * 更新拾光
     *
     * @param id 拾光ID
     * @param complaintDTO 拾光数据
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新拾光", notes = "根据ID更新拾光")
    public Resp<ComplaintDTO> updateComplaint(
            @ApiParam(value = "拾光ID", required = true)
            @PathVariable String id,
            @ApiParam(value = "拾光数据", required = true)
            @RequestBody ComplaintDTO complaintDTO) {
        complaintDTO.setId(id);
        return complaintService.updateComplaint(complaintDTO);
    }
    
    /**
     * 删除拾光
     *
     * @param id 拾光ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除拾光", notes = "根据ID删除拾光")
    public Resp<Boolean> deleteComplaint(
            @ApiParam(value = "拾光ID", required = true)
            @PathVariable String id) {
        return complaintService.deleteComplaint(id);
    }
} 