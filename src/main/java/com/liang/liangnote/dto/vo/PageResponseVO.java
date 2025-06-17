package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页响应视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:45
 */
@Data
@ApiModel(description = "分页响应视图对象")
public class PageResponseVO<T> {
    
    @ApiModelProperty("当前页码")
    private Integer pageNum;
    
    @ApiModelProperty("每页记录数")
    private Integer pageSize;
    
    @ApiModelProperty("总记录数")
    private Long total;
    
    @ApiModelProperty("总页数")
    private Integer pages;
    
    @ApiModelProperty("数据列表")
    private List<T> list;
    
    /**
     * 创建分页响应对象
     *
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @param total 总记录数
     * @param list 数据列表
     * @param <T> 数据类型
     * @return 分页响应对象
     */
    public static <T> PageResponseVO<T> of(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        PageResponseVO<T> pageResponse = new PageResponseVO<>();
        pageResponse.setPageNum(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(total);
        pageResponse.setList(list);
        
        // 计算总页数
        if (pageSize > 0) {
            int pages = (int) ((total + pageSize - 1) / pageSize);
            pageResponse.setPages(pages);
        } else {
            pageResponse.setPages(0);
        }
        
        return pageResponse;
    }
} 