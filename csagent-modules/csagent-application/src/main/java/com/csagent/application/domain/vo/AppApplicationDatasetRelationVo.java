package com.csagent.application.domain.vo;

import com.csagent.application.domain.AppApplicationDatasetRelation;
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
 * 应用知识库关联视图对象 app_application_dataset_relation
 *
 * @author haipeng-lin
 * @date 2026-04-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppApplicationDatasetRelation.class)
public class AppApplicationDatasetRelationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用Id
     */
    @ExcelProperty(value = "应用Id")
    private Long applicationId;

    /**
     * 知识库Id
     */
    @ExcelProperty(value = "知识库Id")
    private Long datasetId;


}
