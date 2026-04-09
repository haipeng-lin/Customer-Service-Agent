package org.dromara.model.domain.vo;

import org.dromara.model.domain.MdModel;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 模型视图对象 md_model
 *
 * @author haipeng-lin
 * @date 2026-04-09
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MdModel.class)
public class MdModelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelProperty(value = "Id")
    private Long id;

    /**
     * 模型名称
     */
    @ExcelProperty(value = "模型名称")
    private String name;

    /**
     * 模型类型（0-语言 1-向量 2-重排）
     */
    @ExcelProperty(value = "模型类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-语言,1=-向量,2=-重排")
    private String type;

    /**
     * 模型标志
     */
    @ExcelProperty(value = "模型标志")
    private String flag;

    /**
     * 模型地址
     */
    @ExcelProperty(value = "模型地址")
    private String address;

    /**
     * 模型key
     */
    @ExcelProperty(value = "模型key")
    private String key;

    /**
     * 可用模型
     */
    @ExcelProperty(value = "可用模型")
    private String models;

    /**
     * 模型图标
     */
    @ExcelProperty(value = "模型图标")
    private String icon;

    /**
     * 状态（0-启用 1-禁用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-启用,1=-禁用")
    private String status;

    /**
     * 修改部门
     */
    @ExcelProperty(value = "修改部门")
    private Long updateDept;


}
