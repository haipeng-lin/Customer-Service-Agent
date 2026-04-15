package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/20 17:32
 * @description: 文档索引状态
 */
@Getter
public enum ParagraphIndexStatus {

    PENDING("0", "待索引"),
    RUNNING("1", "索引中"),
    COMPLETE("2", "已索引"),
    INSERT("3", "已入库");

    private final String code;
    private final String info;

    ParagraphIndexStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
