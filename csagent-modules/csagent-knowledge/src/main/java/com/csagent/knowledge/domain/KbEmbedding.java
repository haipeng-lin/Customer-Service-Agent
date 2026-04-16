package com.csagent.knowledge.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/10 13:54
 * @description: 向量
 */
@Data
//@TableName("kb_embedding")
public class KbEmbedding {

    /**
     * 唯一标识
     */
    private String embeddingId;

    /**
     * 所属的知识库
     */
    private Long datasetId;

    /**
     * 所属文档
     */
    private Long documentId;

    /**
     * 所属段落
     */
    private Long paragraphId;

    /**
     * 向量数据
     */
    private List<Float> embedding;

    /**
     * 全文索引
     */
    private String searchVector;

    /**
     * 状态（0-启用 1-禁用）
     */
    private String status;

    /**
     * 来源类型（0-文档 1-问题）
     */
    private String sourceType;

    /**
     * 来源Id
     */
    private Long sourceId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
