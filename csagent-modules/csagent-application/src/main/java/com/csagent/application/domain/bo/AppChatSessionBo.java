package com.csagent.application.domain.bo;

import com.csagent.application.domain.AppChatSession;
import com.csagent.common.core.validate.EditGroup;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对话会话业务对象 app_chat_session
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppChatSession.class, reverseConvertGenerate = false)
public class AppChatSessionBo extends BaseEntity {

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
