package com.csagent.application.domain.bo;

import lombok.Data;

/**
 * @author haipeng-lin
 * @date 2026/4/15 10:29
 * @description: 回调接口
 */
@Data
public class AppQuestionTestBo {
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
