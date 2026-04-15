package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/16 15:03
 * @description: 模型类别
 */
@Getter
public enum ModelType {


    TEXT("0", "文本"),
    EMBEDDING("1", "向量"),
    RE_SORT("2", "重排");

    private final String code;
    private final String info;

    ModelType(String code, String info) {
        this.code = code;
        this.info = info;
    }


}
