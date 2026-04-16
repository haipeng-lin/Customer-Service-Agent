package com.csagent.application.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.application.domain.AppApplicationDatasetRelation;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 应用知识库关联业务对象 app_application_dataset_relation
 *
 * @author haipeng-lin
 * @date 2026-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppApplicationDatasetRelation.class, reverseConvertGenerate = false)
public class AppApplicationDatasetRelationBo extends BaseEntity {

    /**
     * 应用Id
     */
    @NotNull(message = "应用Id不能为空", groups = {EditGroup.class})
    private Long applicationId;

    /**
     * 知识库Id
     */
    private Long datasetId;


}
