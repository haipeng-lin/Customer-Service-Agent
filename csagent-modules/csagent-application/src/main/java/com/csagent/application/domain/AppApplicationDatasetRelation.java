package com.csagent.application.domain;

import com.csagent.common.tenant.core.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 应用知识库关联对象 app_application_dataset_relation
 *
 * @author haipeng-lin
 * @date 2026-03-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppApplicationDatasetRelation extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用Id
     */
    private Long applicationId;

    /**
     * 知识库Id
     */
    private Long datasetId;

    public AppApplicationDatasetRelation(Long applicationId, Long datasetId, Date createTime) {
        this.applicationId = applicationId;
        this.datasetId = datasetId;
    }
}
