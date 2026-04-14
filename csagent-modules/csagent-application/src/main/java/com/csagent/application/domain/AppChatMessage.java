package com.csagent.application.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 对话消息对象 app_chat_message
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_chat_message")
public class AppChatMessage extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 应用Id
     */
    private Long applicationId;

    /**
     * 会话Id
     */
    private Long sessionId;

    /**
     * 角色（user、agent）
     */
    private String role;

    /**
     * 内容
     */
    private String content;

    /**
     * 消耗Token
     */
    private Long token;

    /**
     * 耗时（毫秒）
     */
    private Long latencyMs;

    /**
     * 评价（0-未评价 1-赞 2-踩）
     */
    private String feedback;


}
