package com.csagent.application.domain;

import com.csagent.knowledge.domain.KbDatasetSimple;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/9 16:23
 * @description: 应用聊天
 */
@Data
public class AppApplicationChat {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 会听id
     */
    private Long sessionId;

    /**
     * id
     */
    private Long applicationId;

    /**
     * 用户输入内容
     */
    private String content;

    /**
     * 知识库列表
     */
    private List<KbDatasetSimple> datasetList;

    /**
     * 运行时上下文id
     */
    private long contextId;

    /**
     * 运行节点id
     */
    private String cell;
}
