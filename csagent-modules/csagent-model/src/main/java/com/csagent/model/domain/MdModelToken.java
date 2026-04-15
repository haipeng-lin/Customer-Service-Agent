package com.csagent.model.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 模型token对象 md_model_token
 *
 * @author haipeng-lin
 * @date 2026-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("md_model_token")
public class MdModelToken extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 模型Id
     */
    private Long modelId;

    /**
     * 消耗来源（0-网页 1-APP）
     */
    private String source;

    /**
     * 输入token数
     */
    private Long inputToken;

    /**
     * 输出token数
     */
    private Long outputToken;

    /**
     * 消耗总token
     */
    private Long totalToken;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 应用Id
     */
    private Long applicationId;

    /**
     * 消息Id
     */
    private Long messageId;

    /**
     * 修改部门
     */
    private Long updateDept;


}
