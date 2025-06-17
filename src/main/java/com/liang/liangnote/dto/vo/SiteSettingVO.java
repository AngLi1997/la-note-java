package com.liang.liangnote.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 网站设置视图对象
 * @author liang
 * @version 1.0.0
 * @date 2025/6/1 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "网站设置视图对象")
public class SiteSettingVO extends BaseVO {

    /**
     * ID
     */
    private String id;

    /**
     * 网站标题
     */
    private String title;

    /**
     * 网站副标题
     */
    private String subtitle;

    /**
     * 网站描述
     */
    private String description;

    /**
     * 网站标语/口号
     */
    private String slogan;

    /**
     * 社交链接列表
     */
    private List<String> socialLinks;

    /**
     * 网站关键词
     */
    private String keywords;

    /**
     * 备案信息
     */
    private String icp;

    /**
     * 网站页脚
     */
    private String footer;
} 