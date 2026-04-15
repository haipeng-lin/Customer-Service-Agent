package com.csagent.application.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用对象 app_application
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_application")
public class AppApplication extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
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
    private Integer topRank;

    /**
     * 重排索引模型
     */
    private Long rerankModelId;

    /**
     * 记忆条数
     */
    private Integer memoryNum;

    /**
     * 回复上限
     */
    private Integer maxReplyToken;

    /**
     * 温度
     */
    private Double temperature;

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

    /**
     * 修改部门
     */
    private Long updateDept;


}
