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
        // 只有当status不为null时才加状态筛选，否则查全部
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(Article::getStatus, queryDTO.getStatus());
        }
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
        // 查询所有分类
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Article::getCategory)
                .groupBy(Article::getCategory)
                .isNotNull(Article::getCategory)
                .ne(Article::getCategory, "");

        List<String> categories = articleMapper.selectList(queryWrapper)
                .stream()
                .map(Article::getCategory)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        return Resp.success(categories);
    }

    @Override
    public Resp<List<String>> listTags() {
        // 查询所有标签
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Article::getTags)
                .isNotNull(Article::getTags)
                .ne(Article::getTags, "");

        List<String> allTags = articleMapper.selectList(queryWrapper)
                .stream()
                .map(Article::getTags)
                .filter(StringUtils::isNotBlank)
                .flatMap(tags -> Arrays.stream(tags.split(",")))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        return Resp.success(allTags);
    }

    @Override
    public Resp<ArticleDTO> getArticleById(String id) {
        // 查询文章
        Article article = articleMapper.selectById(id);

        // 文章不存在
        if (article == null) {
            return Resp.failed("文章不存在");
        }

        // 增加浏览量
        article.setViewCount(article.getViewCount() + 1);
        articleMapper.updateById(article);

        // 转换为DTO
        ArticleDTO articleDTO = convertToDTO(article);

        return Resp.success(articleDTO);
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

    /**
     * 将DTO转换为文章实体
     *
     * @param dto 文章DTO
     * @return 文章实体
     */
    private Article convertToEntity(ArticleDTO dto) {
        Article article = new Article();
        BeanUtils.copyProperties(dto, article);

        // 处理标签
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            article.setTags(String.join(",", dto.getTags()));
        }
        
        // 设置浏览量默认值
        if (article.getViewCount() == null) {
            article.setViewCount(0);
        }
        
        return article;
    }
    
    @Override
    public Resp<ArticleDTO> createArticle(ArticleDTO articleDTO) {
        // 转换为实体
        Article article = convertToEntity(articleDTO);
        
        // 插入数据库
        articleMapper.insert(article);
        
        // 返回结果
        return Resp.success(convertToDTO(article));
    }
    
    @Override
    public Resp<ArticleDTO> updateArticle(ArticleDTO articleDTO) {
        // 校验文章是否存在
        if (StringUtils.isBlank(articleDTO.getId())) {
            return Resp.failed("文章ID不能为空");
        }
        
        Article existingArticle = articleMapper.selectById(articleDTO.getId());
        if (existingArticle == null) {
            return Resp.failed("文章不存在");
        }
        
        // 转换为实体
        Article article = convertToEntity(articleDTO);
        
        // 更新数据库
        articleMapper.updateById(article);
        
        // 返回结果
        return Resp.success(convertToDTO(article));
    }
    
    @Override
    public Resp<Boolean> deleteArticle(String id) {
        // 校验文章是否存在
        if (StringUtils.isBlank(id)) {
            return Resp.failed("文章ID不能为空");
        }
        
        Article existingArticle = articleMapper.selectById(id);
        if (existingArticle == null) {
            return Resp.failed("文章不存在");
        }
        
        // 删除文章（逻辑删除）
        articleMapper.deleteById(id);
        
        return Resp.success(true);
    }
} 