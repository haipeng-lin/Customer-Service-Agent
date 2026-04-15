package com.csagent.common.core.enums;

import lombok.Getter;

@Getter
public enum DocumentType {

    DOCUMENT("0", "文本"),
    QUESTION("1", "问题");

    private final String code;
    private final String info;

    DocumentType(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
