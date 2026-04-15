package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/11 10:21
 * @description: 向量状态
 */
@Getter
public enum EmbeddingStatus {

    PENDING("0", "待向量"),
    RUNNING("1", "向量中"),
    COMPLETE("2", "完成");

    private final String code;
    private final String info;

    EmbeddingStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
