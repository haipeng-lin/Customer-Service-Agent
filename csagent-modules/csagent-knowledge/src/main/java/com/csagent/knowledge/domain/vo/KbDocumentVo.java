package com.csagent.knowledge.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.common.excel.annotation.ExcelDictFormat;
import com.csagent.common.excel.convert.ExcelDictConvert;
import com.csagent.common.translation.annotation.Translation;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.knowledge.domain.KbDocument;
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
     * 知识库标题
     */
    @Translation(type = TransConstant.DATASET_ID_TO_TITLE, mapper = "datasetId")
    private String datasetTitle;

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
     * 状态（0-启用 1-禁用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;


}
