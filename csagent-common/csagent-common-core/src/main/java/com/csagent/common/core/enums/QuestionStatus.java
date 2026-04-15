package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/11 9:43
 * @description: 问题状态
 */
@Getter
public enum QuestionStatus {

    PENDING("0", "待生成"),
    RUNNING("1", "生成中"),
    COMPLETE("2", "完成");

    private final String code;
    private final String info;

    QuestionStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
