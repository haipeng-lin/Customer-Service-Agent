package com.csagent.knowledge.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 知识库对象 kb_dataset
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_dataset")
public class KbDataset extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 向量模型Id
     */
    private Long embeddingModelId;

    /**
     * 向量模型名称
     */
    private String embeddingModelName;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态（0-启用 1-禁用）
     */
    private String status;

}
