package com.csagent.application.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.csagent.application.domain.AppChatMessage;
import com.csagent.common.core.validate.EditGroup;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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

    /**
     * 创建部门
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createDept;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;



}
