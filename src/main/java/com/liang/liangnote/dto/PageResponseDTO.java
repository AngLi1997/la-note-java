package com.liang.liangnote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应数据传输对象
 * @author liang
 * @version 1.0.0
 * @date 2023/11/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页数据响应")
public class PageResponseDTO<T> {

    @ApiModelProperty("当前页码")
    private Integer pageNum;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页数")
    private Integer pages;

    @ApiModelProperty("列表数据")
    private List<T> list;

    /**
     * 创建分页响应
     *
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @param total 总记录数
     * @param list 列表数据
     * @param <T> 数据类型
     * @return 分页响应对象
     */
    public static <T> PageResponseDTO<T> of(Integer pageNum, Integer pageSize, Long total, List<T> list) {
        Integer pages = (int) Math.ceil((double) total / pageSize);
        return PageResponseDTO.<T>builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(total)
                .pages(pages)
                .list(list)
                .build();
    }
} 