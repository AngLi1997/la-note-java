package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.ArticleDTO;
import com.liang.liangnote.dto.ArticleQueryDTO;
import com.liang.liangnote.dto.PageResponseDTO;
import com.liang.liangnote.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章控制器
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@RestController
@RequestMapping("/api/articles")
@Api(tags = "文章接口")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 分页查询文章列表
     *
     * @param queryDTO 查询参数
     * @return 文章分页列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询文章列表", notes = "支持按分类和标签筛选")
    public Resp<PageResponseDTO<ArticleDTO>> listArticles(ArticleQueryDTO queryDTO) {
        return articleService.listArticles(queryDTO);
    }

    /**
     * 根据ID获取文章详情
     *
     * @param id 文章ID
     * @return 文章详情
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详情", notes = "根据文章ID获取详细信息")
    public Resp<ArticleDTO> getArticleById(@PathVariable String id) {
        return articleService.getArticleById(id);
    }

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    @GetMapping("/categories")
    @ApiOperation(value = "获取所有分类", notes = "返回所有文章分类列表")
    public Resp<List<String>> listCategories() {
        return articleService.listCategories();
    }

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    @GetMapping("/tags")
    @ApiOperation(value = "获取所有标签", notes = "返回所有文章标签列表")
    public Resp<List<String>> listTags() {
        return articleService.listTags();
    }
} 