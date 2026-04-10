package com.csagent.knowledge.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.knowledge.domain.KbParagraph;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 段落业务对象 kb_paragraph
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KbParagraph.class, reverseConvertGenerate = false)
public class KbParagraphBo extends BaseEntity {

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
