package com.csagent.application.domain;

import lombok.Data;

/**
 * @author haipeng-lin
 * @date 2026/3/17 9:31
 * @description: 应用知识库关联Dto
 */
@Data
public class AppApplicationDatasetRelations {

    /**
     * 应用Id
     */
    private Long applicationId;

    /**
     * 知识库Id列表
     */
    private Long[] datasetIds;
}
