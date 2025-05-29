package com.liang.liangnote.service;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ArticleDTO;
import com.liang.liangnote.dto.ArticleQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;

import java.util.List;

/**
 * 文章服务接口
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
public interface ArticleService {

    /**
     * 分页查询文章列表
     *
     * @param queryDTO 查询参数
     * @return 文章列表分页结果
     */
    Resp<PageResponseDTO<ArticleDTO>> listArticles(ArticleQueryDTO queryDTO);

    /**
     * 根据ID获取文章详情
     *
     * @param id 文章ID
     * @return 文章详情
     */
    Resp<ArticleDTO> getArticleById(String id);

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    Resp<List<String>> listCategories();

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    Resp<List<String>> listTags();
} 