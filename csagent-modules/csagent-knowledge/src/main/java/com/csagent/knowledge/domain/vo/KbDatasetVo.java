package com.csagent.knowledge.domain.vo;

import com.csagent.knowledge.domain.KbDataset;
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
 * 知识库视图对象 kb_dataset
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbDataset.class)
public class KbDatasetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelProperty(value = "Id")
    private Long id;

    /**
     * 向量模型Id
     */
    @ExcelProperty(value = "向量模型Id")
    private Long embeddingModelId;

    /**
     * 向量模型名称
     */
    @ExcelProperty(value = "向量模型名称")
    private String embeddingModelName;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 状态（0-启用 1-禁用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;


}
