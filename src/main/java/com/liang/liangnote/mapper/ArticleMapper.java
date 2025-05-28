package com.liang.liangnote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.liangnote.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章Repository
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
} 