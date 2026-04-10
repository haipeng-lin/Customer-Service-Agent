package com.csagent.knowledge.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 文档对象 kb_document
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_document")
public class KbDocument extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 知识库Id
     */
    private Long datasetId;

    /**
     * 类型（0-文档 1-文本）
     */
    private String type;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 文件大小（MB）
     */
    private Long fileSize;

    /**
     * 分段数
     */
    private Long segmentCount;

    /**
     * 向量状态（0-待向量 1-向量中 2-完成）
     */
    private String embeddingStatus;

    /**
     * 向量时间
     */
    private Date embeddingTime;

    /**
     * 生成问题状态（0-待生成 1-生成中 2-完成）
     */
    private String questionStatus;

    /**
     * 生成问题时间
     */
    private Date questionTime;

    /**
     * 命中处理方式
     */
    private String answerType;

    /**
     * 返回相似度
     */
    private Long redirectSimilar;

    /**
     * 状态（0-启用 1-禁用）
     */
    private String status;

    /**
     * 修改部门
     */
    private Long updateDept;


}
