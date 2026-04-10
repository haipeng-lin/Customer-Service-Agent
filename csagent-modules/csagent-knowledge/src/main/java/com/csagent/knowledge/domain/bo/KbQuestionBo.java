package com.csagent.knowledge.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.knowledge.domain.KbQuestion;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 问题业务对象 kb_question
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KbQuestion.class, reverseConvertGenerate = false)
public class KbQuestionBo extends BaseEntity {

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
     * 内容
     */
    private String content;

    /**
     * 命中次数
     */
    private Long hitNum;


}
