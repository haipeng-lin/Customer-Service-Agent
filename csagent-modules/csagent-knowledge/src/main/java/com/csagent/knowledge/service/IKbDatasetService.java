package com.csagent.knowledge.service;

import com.csagent.knowledge.domain.vo.KbDatasetVo;
import com.csagent.knowledge.domain.bo.KbDatasetBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
public interface IKbDatasetService {

    /**
     * 查询知识库
     *
     * @param id 主键
     * @return 知识库
     */
    KbDatasetVo queryById(Long id);

    /**
     * 分页查询知识库列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 知识库分页列表
     */
    TableDataInfo<KbDatasetVo> queryPageList(KbDatasetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的知识库列表
     *
     * @param bo 查询条件
     * @return 知识库列表
     */
    List<KbDatasetVo> queryList(KbDatasetBo bo);

    /**
     * 新增知识库
     *
     * @param bo 知识库
     * @return 是否新增成功
     */
    Boolean insertByBo(KbDatasetBo bo);

    /**
     * 修改知识库
     *
     * @param bo 知识库
     * @return 是否修改成功
     */
    Boolean updateByBo(KbDatasetBo bo);

    /**
     * 校验并批量删除知识库信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
