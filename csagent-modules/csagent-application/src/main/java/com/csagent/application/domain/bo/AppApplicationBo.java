package com.csagent.application.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.application.domain.AppApplication;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 应用业务对象 app_application
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppApplication.class, reverseConvertGenerate = false)
public class AppApplicationBo extends BaseEntity {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 应用头像
     */
    private String icon;

    /**
     * 模型Id
     */
    private String modelId;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 是否关联知识库
     */
    private String isRelateDataset;

    /**
     * 开场白标题
     */
    private String prologueTitle;

    /**
     * 开场白问题
     */
    private String prologueQuestion;

    /**
     * 检索模式
     */
    private String searchMode;

    /**
     * 相似度
     */
    private Long similarity;

    /**
     * 召回数量
     */
    private Long topRank;

    /**
     * 重排索引模型
     */
    private Long rerankModelId;

    /**
     * 记忆条数
     */
    private Long memoryNum;

    /**
     * 回复上限
     */
    private Long maxReplyToken;

    /**
     * 温度
     */
    private Long temperature;

    /**
     * 是否显示引用
     */
    private String isShowRelation;

    /**
     * 是否显示时间
     */
    private String isShowTime;

    /**
     * 是否显示token
     */
    private String isShowToken;

    /**
     * 是否显示评价
     */
    private String isShowAppraise;

    /**
     * 状态（0-待发布 1-已发布）
     */
    private String status;


}
