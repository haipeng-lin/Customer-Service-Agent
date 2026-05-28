package com.csagent.application.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author haipeng-lin
 * @date 2026/3/28 8:54
 * @description: 评价
 */
@Data
public class AppAppraise implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息Id
     */
    private Long messageId;

    /**
     * 评价类型（1-赞 2-踩）
     */
    private String feedback;
}
