package com.csagent.knowledge.service;

import com.csagent.knowledge.domain.vo.KbQuestionVo;
import com.csagent.knowledge.domain.bo.KbQuestionBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 问题Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
public interface IKbQuestionService {

    /**
     * 查询问题
     *
     * @param id 主键
     * @return 问题
     */
    KbQuestionVo queryById(Long id);

    /**
     * 分页查询问题列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 问题分页列表
     */
    TableDataInfo<KbQuestionVo> queryPageList(KbQuestionBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的问题列表
     *
     * @param bo 查询条件
     * @return 问题列表
     */
    List<KbQuestionVo> queryList(KbQuestionBo bo);

    /**
     * 新增问题
     *
     * @param bo 问题
     * @return 是否新增成功
     */
    Boolean insertByBo(KbQuestionBo bo);

    /**
     * 修改问题
     *
     * @param bo 问题
     * @return 是否修改成功
     */
    Boolean updateByBo(KbQuestionBo bo);

    /**
     * 校验并批量删除问题信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
