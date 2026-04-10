package com.csagent.knowledge.service;

import com.csagent.knowledge.domain.vo.KbDocumentVo;
import com.csagent.knowledge.domain.bo.KbDocumentBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文档Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
public interface IKbDocumentService {

    /**
     * 查询文档
     *
     * @param id 主键
     * @return 文档
     */
    KbDocumentVo queryById(Long id);

    /**
     * 分页查询文档列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 文档分页列表
     */
    TableDataInfo<KbDocumentVo> queryPageList(KbDocumentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的文档列表
     *
     * @param bo 查询条件
     * @return 文档列表
     */
    List<KbDocumentVo> queryList(KbDocumentBo bo);

    /**
     * 新增文档
     *
     * @param bo 文档
     * @return 是否新增成功
     */
    Boolean insertByBo(KbDocumentBo bo);

    /**
     * 修改文档
     *
     * @param bo 文档
     * @return 是否修改成功
     */
    Boolean updateByBo(KbDocumentBo bo);

    /**
     * 校验并批量删除文档信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
