package com.csagent.knowledge.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/3/10 10:53
 * @description: 知识库简单Vo
 */
@Data
public class KbDatasetSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String datasetId;

    /**
     * 知识库标题
     */
    private String title;
}
