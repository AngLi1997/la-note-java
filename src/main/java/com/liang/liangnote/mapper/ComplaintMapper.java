package com.liang.liangnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.liangnote.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;

/**
 * 吐槽Mapper接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/5
 */
@Mapper
public interface ComplaintMapper extends BaseMapper<Complaint> {
} 