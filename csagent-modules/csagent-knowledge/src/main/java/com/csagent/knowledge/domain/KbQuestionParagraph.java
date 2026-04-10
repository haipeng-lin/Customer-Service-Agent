package com.csagent.knowledge.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 问题段落对象 kb_question_paragraph
 *
 * @author Lion Li
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_question_paragraph")
public class KbQuestionParagraph extends TenantEntity {

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
     * 段落Id
     */
    private Long paragraphId;

    /**
     * 问题Id
     */
    private Long questionId;

    /**
     * 修改部门
     */
    private Long updateDept;


}
