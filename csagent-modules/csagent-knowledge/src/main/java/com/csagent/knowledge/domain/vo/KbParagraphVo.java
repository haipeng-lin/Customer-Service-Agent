package com.csagent.knowledge.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.common.excel.annotation.ExcelDictFormat;
import com.csagent.common.excel.convert.ExcelDictConvert;
import com.csagent.common.translation.annotation.Translation;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.knowledge.domain.KbParagraph;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 段落视图对象 kb_paragraph
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbParagraph.class)
public class KbParagraphVo implements Serializable {

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
     * 文档Id
     */
    @ExcelProperty(value = "文档Id")
    private Long documentId;

    /**
     * 文档标题
     */
    @Translation(type = TransConstant.DOCUMENT_ID_TO_TITLE, mapper = "documentId")
    private String documentTitle;

    /**
     * 段落标题
     */
    @ExcelProperty(value = "段落标题")
    private String title;

    /**
     * 段落内容
     */
    @ExcelProperty(value = "段落内容")
    private String content;

    /**
     * 索引状态（0-待索引 1-索引中 2-完成）
     */
    @ExcelProperty(value = "索引状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-待索引,1=-索引中,2=-完成")
    private String indexStatus;

    /**
     * 状态（0-启用 1-禁用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;


}
