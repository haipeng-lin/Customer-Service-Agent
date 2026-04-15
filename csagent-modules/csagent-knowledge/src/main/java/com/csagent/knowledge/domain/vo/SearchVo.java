package com.csagent.knowledge.domain.vo;

import com.csagent.common.tenant.core.TenantEntity;
import lombok.Data;

import java.io.Serial;

/**
 * @author haipeng-lin
 * @date 2026/3/9 17:55
 * @description: 检索Vo
 */
@Data
public class SearchVo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 段落id
     */
    private Long paragraphId;

    /**
     * 相似度
     */
    private double similarity;

    /**
     * 综合得分
     */
    private double comprehensiveScore;

    /**
     * 段落标题
     */
    private String title;

    /**
     * 段落内容
     */
    private String content;

    /**
     * 文档id
     */
    private Long documentId;

    /**
     * 文档名称
     */
    private String documentName;
}
