package org.dromara.model.domain.bo;

import com.csagent.model.domain.MdModel;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 模型业务对象 md_model
 *
 * @author haipeng-lin
 * @date 2026-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MdModel.class, reverseConvertGenerate = false)
public class MdModelBo extends BaseEntity {

    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = { EditGroup.class })
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
