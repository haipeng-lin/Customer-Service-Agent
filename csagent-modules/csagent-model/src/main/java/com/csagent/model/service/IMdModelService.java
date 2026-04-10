package com.csagent.model.service;

import com.csagent.common.core.validate.EditGroup;
import com.csagent.model.domain.vo.MdModelVo;
import com.csagent.model.domain.bo.MdModelBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * 模型Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-09
 */
public interface IMdModelService {

    /**
     * 查询模型
     *
     * @param id 主键
     * @return 模型
     */
    MdModelVo queryById(Long id);

    /**
     * 分页查询模型列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 模型分页列表
     */
    TableDataInfo<MdModelVo> queryPageList(MdModelBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的模型列表
     *
     * @param bo 查询条件
     * @return 模型列表
     */
    List<MdModelVo> queryList(MdModelBo bo);

    /**
     * 新增模型
     *
     * @param bo 模型
     * @return 是否新增成功
     */
    Boolean insertByBo(MdModelBo bo);

    /**
     * 修改模型
     *
     * @param bo 模型
     * @return 是否修改成功
     */
    Boolean updateByBo(MdModelBo bo);

    /**
     * 校验并批量删除模型信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 修改状态
     *
     * @param id     Id
     * @param status 状态
     * @return 结果
     */
    int updateStatus(Long id, String status);
}
