package com.csagent.application.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.application.domain.AppChatMessage;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 对话消息业务对象 app_chat_message
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppChatMessage.class, reverseConvertGenerate = false)
public class AppChatMessageBo extends BaseEntity {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = {EditGroup.class})
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
