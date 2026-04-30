package com.csagent.model.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.common.excel.annotation.ExcelDictFormat;
import com.csagent.common.excel.convert.ExcelDictConvert;
import com.csagent.common.translation.annotation.Translation;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.model.domain.MdModelToken;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 模型token视图对象 md_model_token
 *
 * @author haipeng-lin
 * @date 2026-04-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MdModelToken.class)
public class MdModelTokenVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelProperty(value = "Id")
    private Long id;

    /**
     * 模型Id
     */
    @ExcelProperty(value = "模型Id")
    private Long modelId;

    /**
     * 模型名称
     */
    @Translation(type = TransConstant.MODEL_ID_TO_NAME, mapper = "modelId")
    private String modelName;

    /**
     * 消耗来源（0-网页 1-APP）
     */
    @ExcelProperty(value = "消耗来源", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "app_session_source_type")
    private String source;

    /**
     * 输入token数
     */
    @ExcelProperty(value = "输入token数")
    private Long inputToken;

    /**
     * 输出token数
     */
    @ExcelProperty(value = "输出token数")
    private Long outputToken;

    /**
     * 消耗总token
     */
    @ExcelProperty(value = "消耗总token")
    private Long totalToken;

    /**
     * 用户Id
     */
    @ExcelProperty(value = "用户Id")
    private Long userId;

    /**
     * 用户名
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "userId")
    private String username;

    /**
     * 应用Id
     */
    @ExcelProperty(value = "应用Id")
    private Long applicationId;

    /**
     * 消息Id
     */
    @ExcelProperty(value = "消息Id")
    private Long messageId;


}
