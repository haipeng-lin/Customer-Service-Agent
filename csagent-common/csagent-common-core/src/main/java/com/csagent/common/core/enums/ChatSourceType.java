package com.csagent.common.core.enums;

import lombok.Getter;

/**
 * @author haipeng-lin
 * @date 2026/3/24 17:36
 * @description: 对话来源类型
 */
@Getter
public enum ChatSourceType {

    WEB("0", "网页 "),
    APP("1", "App"),
    MINI_APP("2", "小程序");

    private final String code;
    private final String info;

    ChatSourceType(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
