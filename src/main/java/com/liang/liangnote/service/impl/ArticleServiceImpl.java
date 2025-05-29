package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ArticleDTO;
import com.liang.liangnote.dto.ArticleQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.entity.Article;
import com.liang.liangnote.mapper.ArticleMapper;
import com.liang.liangnote.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Resp<PageResponseDTO<ArticleDTO>> listArticles(ArticleQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        // 只查询已发布的文章
        queryWrapper.eq(Article::getStatus, 1);
        // 按分类筛选
        if (StringUtils.isNotBlank(queryDTO.getCategory()) && !"all".equalsIgnoreCase(queryDTO.getCategory())) {
            queryWrapper.eq(Article::getCategory, queryDTO.getCategory());
        }
        // 按标签筛选
        if (StringUtils.isNotBlank(queryDTO.getTag())) {
            queryWrapper.like(Article::getTags, queryDTO.getTag());
        }
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Article::getId);

        // 执行分页查询
        Page<Article> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);

        // 转换为DTO
        List<ArticleDTO> articleDTOs = articlePage.getRecords().stream().map(this::convertToDTO).collect(Collectors.toList());

        // 构造分页响应
        PageResponseDTO<ArticleDTO> pageResponse = PageResponseDTO.of(
                queryDTO.getPageNum(),
                queryDTO.getPageSize(),
                articlePage.getTotal(),
                articleDTOs
        );

        return Resp.success(pageResponse);
    }

    @Override
    public Resp<List<String>> listCategories() {
        // 查询所有发布的文章
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Article::getStatus, 1);
        queryWrapper.select(Article::getCategory);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        // 提取所有不重复的分类
        List<String> categories = articles.stream()
                .map(Article::getCategory)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        return Resp.success(categories);
    }

    @Override
    public Resp<List<String>> listTags() {
        // 查询所有发布的文章
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Article::getStatus, 1);
        queryWrapper.select(Article::getTags);
        List<Article> articles = articleMapper.selectList(queryWrapper);

        // 提取所有不重复的标签
        List<String> allTags = new ArrayList<>();
        articles.stream()
                .map(Article::getTags)
                .filter(StringUtils::isNotBlank)
                .forEach(tagStr -> {
                    String[] tagArray = tagStr.split(",");
                    allTags.addAll(Arrays.asList(tagArray));
                });

        return Resp.success(allTags.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * 将文章实体转换为DTO
     *
     * @param article 文章实体
     * @return 文章DTO
     */
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        BeanUtils.copyProperties(article, dto);
        
        // 处理标签
        if (StringUtils.isNotBlank(article.getTags())) {
            dto.setTags(Arrays.asList(article.getTags().split(",")));
        } else {
            dto.setTags(new ArrayList<>());
        }
        
        return dto;
    }
} 