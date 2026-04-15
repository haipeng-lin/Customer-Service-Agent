package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/20 16:05
 * @description: 来源类型
 */
@Getter
public enum EmbeddingSourceType {

    DOCUMENT("0", "文档"),
    QUESTION("1", "问题");

    private final String code;
    private final String info;

    EmbeddingSourceType(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
