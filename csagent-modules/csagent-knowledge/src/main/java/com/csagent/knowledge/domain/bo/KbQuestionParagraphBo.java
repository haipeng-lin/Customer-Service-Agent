package com.csagent.knowledge.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.knowledge.domain.KbQuestionParagraph;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 问题段落业务对象 kb_question_paragraph
 *
 * @author Lion Li
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KbQuestionParagraph.class, reverseConvertGenerate = false)
public class KbQuestionParagraphBo extends BaseEntity {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = {EditGroup.class})
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


}
