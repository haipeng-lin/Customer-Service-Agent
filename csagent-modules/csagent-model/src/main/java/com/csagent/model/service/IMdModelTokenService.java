package com.csagent.model.service;

import com.csagent.model.domain.vo.MdModelTokenVo;
import com.csagent.model.domain.bo.MdModelTokenBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 模型tokenService接口
 *
 * @author haipeng-lin
 * @date 2026-04-15
 */
public interface IMdModelTokenService {

    /**
     * 查询模型token
     *
     * @param id 主键
     * @return 模型token
     */
    MdModelTokenVo queryById(Long id);

    /**
     * 分页查询模型token列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 模型token分页列表
     */
    TableDataInfo<MdModelTokenVo> queryPageList(MdModelTokenBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的模型token列表
     *
     * @param bo 查询条件
     * @return 模型token列表
     */
    List<MdModelTokenVo> queryList(MdModelTokenBo bo);

    /**
     * 新增模型token
     *
     * @param bo 模型token
     * @return 是否新增成功
     */
    Boolean insertByBo(MdModelTokenBo bo);

    /**
     * 修改模型token
     *
     * @param bo 模型token
     * @return 是否修改成功
     */
    Boolean updateByBo(MdModelTokenBo bo);

    /**
     * 校验并批量删除模型token信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
