package com.csagent.application.domain.vo;

import com.csagent.application.domain.AppChatSession;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.common.excel.annotation.ExcelDictFormat;
import com.csagent.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 对话会话视图对象 app_chat_session
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppChatSession.class)
public class AppChatSessionVo implements Serializable {

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
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 来源（0-网页 1-APP）
     */
    @ExcelProperty(value = "来源", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "app_session_source_type")
    private String source;

    /**
     * 状态（0-AI 1-人工 2-结束）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "app_session_status")
    private String status;


}
