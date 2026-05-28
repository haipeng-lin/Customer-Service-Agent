package com.csagent.common.core.enums;

import lombok.Getter;

@Getter
public enum ChatRoleType {

    USER("user", "用户 "),
    AGENT("agent", "AI");

    private final String code;
    private final String info;

    ChatRoleType(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
