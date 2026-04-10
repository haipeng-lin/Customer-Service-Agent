package com.csagent.knowledge.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.knowledge.domain.KbDataset;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 知识库业务对象 kb_dataset
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = KbDataset.class, reverseConvertGenerate = false)
public class KbDatasetBo extends BaseEntity {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = {EditGroup.class})
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
