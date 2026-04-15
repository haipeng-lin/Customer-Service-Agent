package com.csagent.knowledge.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.csagent.common.tenant.core.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/10 13:54
 * @description: 向量
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_embedding")
public class KbEmbedding extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private String embeddingId;

    /**
     * 所属的知识库
     */
    private String datasetId;

    /**
     * 所属文档
     */
    private String documentId;

    /**
     * 所属段落
     */
    private String paragraphId;

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
    private Integer status;

    /**
     * 来源类型（0-文档 1-问题）
     */
    private Integer sourceType;

    /**
     * 来源Id
     */
    private String sourceId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
