package com.csagent.knowledge.service;

import com.csagent.knowledge.domain.vo.KbQuestionParagraphVo;
import com.csagent.knowledge.domain.bo.KbQuestionParagraphBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 问题段落Service接口
 *
 * @author Lion Li
 * @date 2026-04-10
 */
public interface IKbQuestionParagraphService {

    /**
     * 查询问题段落
     *
     * @param id 主键
     * @return 问题段落
     */
    KbQuestionParagraphVo queryById(Long id);

    /**
     * 分页查询问题段落列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 问题段落分页列表
     */
    TableDataInfo<KbQuestionParagraphVo> queryPageList(KbQuestionParagraphBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的问题段落列表
     *
     * @param bo 查询条件
     * @return 问题段落列表
     */
    List<KbQuestionParagraphVo> queryList(KbQuestionParagraphBo bo);

    /**
     * 新增问题段落
     *
     * @param bo 问题段落
     * @return 是否新增成功
     */
    Boolean insertByBo(KbQuestionParagraphBo bo);

    /**
     * 修改问题段落
     *
     * @param bo 问题段落
     * @return 是否修改成功
     */
    Boolean updateByBo(KbQuestionParagraphBo bo);

    /**
     * 校验并批量删除问题段落信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
