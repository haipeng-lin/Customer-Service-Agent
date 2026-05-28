package com.csagent.application.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.application.domain.AppChatMessage;
import com.csagent.common.excel.annotation.ExcelDictFormat;
import com.csagent.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 对话消息视图对象 app_chat_message
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppChatMessage.class)
public class AppChatMessageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelProperty(value = "Id")
    private Long id;

    /**
     * 用户Id
     */
    @ExcelProperty(value = "用户Id")
    private Long userId;

    /**
     * 应用Id
     */
    @ExcelProperty(value = "应用Id")
    private Long applicationId;

    /**
     * 会话Id
     */
    @ExcelProperty(value = "会话Id")
    private Long sessionId;

    /**
     * 角色（user、agent）
     */
    @ExcelProperty(value = "角色", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "app_role_type")
    private String role;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 消耗Token
     */
    @ExcelProperty(value = "消耗Token")
    private Long token;

    /**
     * 耗时（毫秒）
     */
    @ExcelProperty(value = "耗时", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "毫=秒")
    private Long latencyMs;

    /**
     * 评价（0-未评价 1-赞 2-踩）
     */
    @ExcelProperty(value = "评价", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "app_feedback_type")
    private String feedback;

    /**
     * 来源内容
     */
    @ExcelProperty(value = "来源内容")
    private String sourceContent;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String createTime;


}
