package com.csagent.knowledge.domain;

import lombok.Data;

/**
 * @author haipeng-lin
 * @date 2026/4/16 11:50
 * @description: 问题生成
 */
@Data
public class KbQuestionGeneration {

    /**
     * 文档Id
     */
    private String documentIds;

    /**
     * 模型Id
     */
    private Long modelId;

    /**
     * 提示词
     */
    private String prompt;

}
