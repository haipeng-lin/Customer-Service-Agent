package com.csagent.knowledge.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.csagent.knowledge.domain.KbDocument;
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
 * 文档视图对象 kb_document
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbDocument.class)
public class KbDocumentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelProperty(value = "Id")
    private Long id;

    /**
     * 知识库Id
     */
    @ExcelProperty(value = "知识库Id")
    private Long datasetId;

    /**
     * 类型（0-文档 1-文本）
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-文档,1=-文本")
    private String type;

    /**
     * 文档标题
     */
    @ExcelProperty(value = "文档标题")
    private String title;

    /**
     * 文档内容
     */
    @ExcelProperty(value = "文档内容")
    private String content;

    /**
     * 文件大小（MB）
     */
    @ExcelProperty(value = "文件大小", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "M=B")
    private Long fileSize;

    /**
     * 分段数
     */
    @ExcelProperty(value = "分段数")
    private Long segmentCount;

    /**
     * 向量状态（0-待向量 1-向量中 2-完成）
     */
    @ExcelProperty(value = "向量状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "kb_embedding_status")
    private String embeddingStatus;

    /**
     * 向量时间
     */
    @ExcelProperty(value = "向量时间")
    private Date embeddingTime;

    /**
     * 生成问题状态（0-待生成 1-生成中 2-完成）
     */
    @ExcelProperty(value = "生成问题状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "kb_question_status")
    private String questionStatus;

    /**
     * 生成问题时间
     */
    @ExcelProperty(value = "生成问题时间")
    private Date questionTime;

    /**
     * 命中处理方式
     */
    @ExcelProperty(value = "命中处理方式")
    private String answerType;

    /**
     * 返回相似度
     */
    @ExcelProperty(value = "返回相似度")
    private Long redirectSimilar;

    /**
     * 状态（0-启用 1-禁用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;


}
