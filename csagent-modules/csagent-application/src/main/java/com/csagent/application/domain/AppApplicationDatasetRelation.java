package com.csagent.application.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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

    /**
     * 更新者
     */
    @TableField(exist = false)
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(exist = false)
    private Date updateTime;

}
