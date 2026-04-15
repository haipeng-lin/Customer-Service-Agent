package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/11 10:36
 * @description: 通用状态
 */
@Getter
public enum UniversalStatus {

    ENABLE("0", "启用 "),
    DISABLE("1", "禁用");

    private final String code;
    private final String info;

    UniversalStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
