package com.csagent.knowledge.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 问题对象 kb_question
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_question")
public class KbQuestion extends TenantEntity {

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
     * 内容
     */
    private String content;

    /**
     * 命中次数
     */
    private Integer hitNum;

}
