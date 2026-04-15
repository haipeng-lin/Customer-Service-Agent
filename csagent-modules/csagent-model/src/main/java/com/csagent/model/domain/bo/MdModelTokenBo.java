package com.csagent.model.domain.bo;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.model.domain.MdModelToken;
import com.csagent.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 模型token业务对象 md_model_token
 *
 * @author haipeng-lin
 * @date 2026-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MdModelToken.class, reverseConvertGenerate = false)
public class MdModelTokenBo extends BaseEntity {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = {EditGroup.class})
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


}
