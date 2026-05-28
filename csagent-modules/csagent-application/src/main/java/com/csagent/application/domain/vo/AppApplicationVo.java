package com.csagent.application.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import com.csagent.application.domain.AppApplication;
import com.csagent.common.excel.annotation.ExcelDictFormat;
import com.csagent.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 应用视图对象 app_application
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppApplication.class)
public class AppApplicationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @ExcelProperty(value = "Id")
    private Long id;

    /**
     * 应用名称
     */
    @ExcelProperty(value = "应用名称")
    private String name;

    /**
     * 应用描述
     */
    @ExcelProperty(value = "应用描述")
    private String description;

    /**
     * 应用头像
     */
    @ExcelProperty(value = "应用头像")
    private String icon;

    /**
     * 模型Id
     */
    @ExcelProperty(value = "模型Id")
    private String modelId;

    /**
     * 模型名称
     */
    @ExcelProperty(value = "模型名称")
    private String modelName;

    /**
     * 提示词
     */
    @ExcelProperty(value = "提示词")
    private String prompt;

    /**
     * 是否关联知识库
     */
    @ExcelProperty(value = "是否关联知识库", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String isRelateDataset;

    /**
     * 开场白标题
     */
    @ExcelProperty(value = "开场白标题")
    private String prologueTitle;

    /**
     * 开场白问题
     */
    @ExcelProperty(value = "开场白问题")
    private String prologueQuestion;

    /**
     * 检索模式
     */
    @ExcelProperty(value = "检索模式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "app_search_type")
    private String searchMode;

    /**
     * 相似度
     */
    @ExcelProperty(value = "相似度")
    private Double similarity;

    /**
     * 召回数量
     */
    @ExcelProperty(value = "召回数量")
    private Integer topRank;

    /**
     * 重排索引模型
     */
    @ExcelProperty(value = "重排索引模型")
    private Long rerankModelId;

    /**
     * 记忆条数
     */
    @ExcelProperty(value = "记忆条数")
    private Integer memoryNum;

    /**
     * 回复上限
     */
    @ExcelProperty(value = "回复上限")
    private Integer maxReplyToken;

    /**
     * 温度
     */
    @ExcelProperty(value = "温度")
    private Double temperature;

    /**
     * 是否显示引用
     */
    @ExcelProperty(value = "是否显示引用", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String isShowRelation;

    /**
     * 是否显示时间
     */
    @ExcelProperty(value = "是否显示时间", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String isShowTime;

    /**
     * 是否显示token
     */
    @ExcelProperty(value = "是否显示token", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String isShowToken;

    /**
     * 是否显示评价
     */
    @ExcelProperty(value = "是否显示评价", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String isShowAppraise;

    /**
     * 状态（0-待发布 1-已发布）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;


}
