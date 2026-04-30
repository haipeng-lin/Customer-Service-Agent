package com.csagent.knowledge.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 段落对象 kb_paragraph
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_paragraph")
public class KbParagraph extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 知识库Id
     */
    private Long datasetId;

    /**
     * 文档Id
     */
    private Long documentId;

    /**
     * 段落标题
     */
    private String title;

    /**
     * 段落内容
     */
    private String content;

    /**
     * 索引状态（0-待索引 1-索引中 2-完成）
     */
    private String indexStatus;

    /**
     * 状态（0-启用 1-禁用）
     */
    private String status;


}
