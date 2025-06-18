package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ArticleDTO;
import com.liang.liangnote.dto.ArticleQueryDTO;
import com.liang.liangnote.dto.vo.ArticleVO;
import com.liang.liangnote.dto.vo.PageResponseVO;
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
    public Resp<PageResponseVO<ArticleVO>> listArticles(ArticleQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果不需要内容，则排除content字段
        if (queryDTO.getIncludeContent() == null || !queryDTO.getIncludeContent()) {
            queryWrapper.select(Article.class, info -> !info.getColumn().equals("content"));
        }
        
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

        // 转换为VO
        List<ArticleVO> articleVOs;
        if (queryDTO.getIncludeContent() != null && queryDTO.getIncludeContent()) {
            // 如果需要内容，使用完整转换
            articleVOs = articlePage.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        } else {
            // 如果不需要内容，使用列表转换（不包含content）
            articleVOs = articlePage.getRecords().stream().map(this::convertToListVO).collect(Collectors.toList());
        }

        // 构造分页响应
        PageResponseVO<ArticleVO> pageResponse = PageResponseVO.of(
                queryDTO.getPageNum(),
                queryDTO.getPageSize(),
                articlePage.getTotal(),
                articleVOs
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
    public Resp<ArticleVO> getArticleById(String id) {
        // 查询文章
        Article article = articleMapper.selectById(id);

        // 文章不存在
        if (article == null) {
            return Resp.failed("文章不存在");
        }

        // 增加浏览量
        article.setViewCount(article.getViewCount() + 1);
        articleMapper.updateById(article);

        // 转换为VO
        ArticleVO articleVO = convertToVO(article);

        return Resp.success(articleVO);
    }

    /**
     * 将文章实体转换为VO（完整版，包含content）
     *
     * @param article 文章实体
     * @return 文章VO
     */
    private ArticleVO convertToVO(Article article) {
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);

        // 处理标签
        if (StringUtils.isNotBlank(article.getTags())) {
            vo.setTags(Arrays.asList(article.getTags().split(",")));
        } else {
            vo.setTags(new ArrayList<>());
        }

        return vo;
    }
    
    /**
     * 将文章实体转换为列表VO（不包含content）
     *
     * @param article 文章实体
     * @return 文章列表VO
     */
    private ArticleVO convertToListVO(Article article) {
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);
        
        // 处理标签
        if (StringUtils.isNotBlank(article.getTags())) {
            vo.setTags(Arrays.asList(article.getTags().split(",")));
        } else {
            vo.setTags(new ArrayList<>());
        }
        
        // 生成内容预览
        if (StringUtils.isNotBlank(article.getSummary())) {
            // 如果有摘要，优先使用摘要作为预览
            vo.setContentPreview(article.getSummary());
        } else if (StringUtils.isNotBlank(article.getContent())) {
            // 如果内容过长，截取前100个字符作为预览
            int previewLength = 100;
            String content = article.getContent();
            if (content.length() > previewLength) {
                vo.setContentPreview(content.substring(0, previewLength) + "...");
            } else {
                vo.setContentPreview(content);
            }
        }
        
        // 确保不返回内容
        vo.setContent(null);
        
        return vo;
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
    public Resp<ArticleVO> createArticle(ArticleDTO articleDTO) {
        // 转换为实体
        Article article = convertToEntity(articleDTO);
        
        // 插入数据库
        articleMapper.insert(article);
        
        // 返回结果
        return Resp.success(convertToVO(article));
    }
    
    @Override
    public Resp<ArticleVO> updateArticle(ArticleDTO articleDTO) {
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
        
        // 查询更新后的文章并返回
        Article updatedArticle = articleMapper.selectById(article.getId());
        
        // 返回结果
        return Resp.success(convertToVO(updatedArticle));
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