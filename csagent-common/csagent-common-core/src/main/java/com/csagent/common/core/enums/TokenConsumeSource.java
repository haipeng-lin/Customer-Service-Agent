package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/19 14:58
 * @description: token消耗来源
 */
@Getter
public enum TokenConsumeSource {


    CHAT("chat", "对话"),
    EMBEDDING("embedding", "向量"),
    QUESTION("question", "问题");


    private final String code;
    private final String info;

    TokenConsumeSource(String code, String info) {
        this.code = code;
        this.info = info;
    }


}
