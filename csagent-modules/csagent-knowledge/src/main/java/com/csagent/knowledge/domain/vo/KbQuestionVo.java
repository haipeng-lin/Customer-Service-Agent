package com.csagent.knowledge.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.common.translation.annotation.Translation;
import com.csagent.common.translation.constant.TransConstant;
import com.csagent.knowledge.domain.KbQuestion;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 问题视图对象 kb_question
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbQuestion.class)
public class KbQuestionVo implements Serializable {

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
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 命中次数
     */
    @ExcelProperty(value = "命中次数")
    private Long hitNum;


}
