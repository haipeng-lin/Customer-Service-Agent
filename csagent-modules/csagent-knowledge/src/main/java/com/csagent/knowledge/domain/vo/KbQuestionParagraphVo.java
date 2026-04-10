package com.csagent.knowledge.domain.vo;

import com.csagent.knowledge.domain.KbQuestionParagraph;
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
 * 问题段落视图对象 kb_question_paragraph
 *
 * @author Lion Li
 * @date 2026-04-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbQuestionParagraph.class)
public class KbQuestionParagraphVo implements Serializable {

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
     * 文档Id
     */
    @ExcelProperty(value = "文档Id")
    private Long documentId;

    /**
     * 段落Id
     */
    @ExcelProperty(value = "段落Id")
    private Long paragraphId;

    /**
     * 问题Id
     */
    @ExcelProperty(value = "问题Id")
    private Long questionId;


}
