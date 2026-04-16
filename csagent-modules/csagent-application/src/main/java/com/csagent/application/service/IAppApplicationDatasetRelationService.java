package com.csagent.application.service;

import com.csagent.application.domain.vo.AppApplicationDatasetRelationVo;
import com.csagent.application.domain.bo.AppApplicationDatasetRelationBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 应用知识库关联Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-16
 */
public interface IAppApplicationDatasetRelationService {

    /**
     * 查询应用知识库关联
     *
     * @param applicationId 主键
     * @return 应用知识库关联
     */
    AppApplicationDatasetRelationVo queryById(Long applicationId);

    /**
     * 分页查询应用知识库关联列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 应用知识库关联分页列表
     */
    TableDataInfo<AppApplicationDatasetRelationVo> queryPageList(AppApplicationDatasetRelationBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的应用知识库关联列表
     *
     * @param bo 查询条件
     * @return 应用知识库关联列表
     */
    List<AppApplicationDatasetRelationVo> queryList(AppApplicationDatasetRelationBo bo);

    /**
     * 新增应用知识库关联
     *
     * @param bo 应用知识库关联
     * @return 是否新增成功
     */
    Boolean insertByBo(AppApplicationDatasetRelationBo bo);

    /**
     * 修改应用知识库关联
     *
     * @param bo 应用知识库关联
     * @return 是否修改成功
     */
    Boolean updateByBo(AppApplicationDatasetRelationBo bo);

    /**
     * 校验并批量删除应用知识库关联信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
