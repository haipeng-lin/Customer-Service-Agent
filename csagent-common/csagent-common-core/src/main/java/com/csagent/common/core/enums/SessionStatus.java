package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/31 14:26
 * @description: 会话状态
 */
@Getter
public enum SessionStatus {

    PENDING("0", "AI"),
    RUNNING("1", "人工"),
    COMPLETE("2", "结束"),
    DELETE("3", "删除");


    private final String code;
    private final String info;

    SessionStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
