package com.csagent.model.domain;

import com.csagent.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 模型对象 md_model
 *
 * @author haipeng-lin
 * @date 2026-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("md_model")
public class MdModel extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 模型类型（0-语言 1-向量 2-重排）
     */
    private String type;

    /**
     * 模型标志
     */
    private String flag;

    /**
     * 模型地址
     */
    private String address;

    /**
     * 模型key
     */
    @TableField("`key`")
    private String key;

    /**
     * 可用模型
     */
    private String models;

    /**
     * 模型图标
     */
    private String icon;

    /**
     * 状态（0-启用 1-禁用）
     */
    private String status;

    /**
     * 修改部门
     */
    private Long updateDept;


}
