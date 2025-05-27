package com.liang.liangnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.liangnote.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
} 