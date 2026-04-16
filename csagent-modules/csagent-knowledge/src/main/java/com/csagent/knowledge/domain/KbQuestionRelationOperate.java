package com.csagent.knowledge.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/4/2 16:38
 * @description: 问题关联操作
 */
@Data
public class KbQuestionRelationOperate {

    /**
     * 知识库Id
     */
    private Long datasetId;

    /**
     * 文档Id
     */
    private Long documentId;

    /**
     * 段落Id
     */
    private Long paragraphId;

    /**
     * 问题Ids
     */
    private String questionIds;

    /**
     * 操作类型（0-新增 1-删除）
     */
    private Integer type;
}
