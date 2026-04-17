package com.csagent.knowledge.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import com.csagent.knowledge.domain.KbDocument;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文档业务对象 kb_document
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KbDocument.class, reverseConvertGenerate = false)
public class KbDocumentBo extends BaseEntity {

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
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 文件大小（MB）
     */
    private Long fileSize;

    /**
     * 分段数
     */
    private Long segmentCount;

    /**
     * 向量状态（0-待向量 1-向量中 2-完成）
     */
    private String embeddingStatus;

    /**
     * 向量时间
     */
    private Date embeddingTime;

    /**
     * 生成问题状态（0-待生成 1-生成中 2-完成）
     */
    private String questionStatus;

    /**
     * 生成问题时间
     */
    private Date questionTime;

    /**
     * 状态（0-启用 1-禁用）
     */
    private String status;


}
