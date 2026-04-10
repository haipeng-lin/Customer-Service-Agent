package com.csagent.knowledge.service;

import com.csagent.knowledge.domain.vo.KbParagraphVo;
import com.csagent.knowledge.domain.bo.KbParagraphBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 段落Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
public interface IKbParagraphService {

    /**
     * 查询段落
     *
     * @param id 主键
     * @return 段落
     */
    KbParagraphVo queryById(Long id);

    /**
     * 分页查询段落列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 段落分页列表
     */
    TableDataInfo<KbParagraphVo> queryPageList(KbParagraphBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的段落列表
     *
     * @param bo 查询条件
     * @return 段落列表
     */
    List<KbParagraphVo> queryList(KbParagraphBo bo);

    /**
     * 新增段落
     *
     * @param bo 段落
     * @return 是否新增成功
     */
    Boolean insertByBo(KbParagraphBo bo);

    /**
     * 修改段落
     *
     * @param bo 段落
     * @return 是否修改成功
     */
    Boolean updateByBo(KbParagraphBo bo);

    /**
     * 校验并批量删除段落信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 修改状态
     *
     * @param id     主键
     * @param status 状态
     * @return 是否修改成功
     */
    int updateStatus(Long id, String status);
}
