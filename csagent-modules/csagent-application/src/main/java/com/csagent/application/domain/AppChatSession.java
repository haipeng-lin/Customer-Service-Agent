package com.csagent.application.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 对话会话对象 app_chat_session
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_chat_session")
public class AppChatSession extends TenantEntity {

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
     * 标题
     */
    private String title;

    /**
     * 来源（0-网页 1-APP）
     */
    private String source;

    /**
     * 状态（0-AI 1-人工 2-结束）
     */
    private String status;


}
