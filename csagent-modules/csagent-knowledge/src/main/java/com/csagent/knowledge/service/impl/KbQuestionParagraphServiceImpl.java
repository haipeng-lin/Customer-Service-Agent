package com.csagent.knowledge.service.impl;

import com.csagent.common.core.utils.MapstructUtils;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.csagent.knowledge.domain.bo.KbQuestionParagraphBo;
import com.csagent.knowledge.domain.vo.KbQuestionParagraphVo;
import com.csagent.knowledge.domain.KbQuestionParagraph;
import com.csagent.knowledge.mapper.KbQuestionParagraphMapper;
import com.csagent.knowledge.service.IKbQuestionParagraphService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 问题段落Service业务层处理
 *
 * @author Lion Li
 * @date 2026-04-10
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KbQuestionParagraphServiceImpl implements IKbQuestionParagraphService {

    private final KbQuestionParagraphMapper baseMapper;

    /**
     * 查询问题段落
     *
     * @param id 主键
     * @return 问题段落
     */
    @Override
    public KbQuestionParagraphVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询问题段落列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 问题段落分页列表
     */
    @Override
    public TableDataInfo<KbQuestionParagraphVo> queryPageList(KbQuestionParagraphBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<KbQuestionParagraph> lqw = buildQueryWrapper(bo);
        Page<KbQuestionParagraphVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的问题段落列表
     *
     * @param bo 查询条件
     * @return 问题段落列表
     */
    @Override
    public List<KbQuestionParagraphVo> queryList(KbQuestionParagraphBo bo) {
        LambdaQueryWrapper<KbQuestionParagraph> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbQuestionParagraph> buildQueryWrapper(KbQuestionParagraphBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KbQuestionParagraph> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(KbQuestionParagraph::getId);
        lqw.eq(bo.getDatasetId() != null, KbQuestionParagraph::getDatasetId, bo.getDatasetId());
        lqw.eq(bo.getDocumentId() != null, KbQuestionParagraph::getDocumentId, bo.getDocumentId());
        lqw.eq(bo.getParagraphId() != null, KbQuestionParagraph::getParagraphId, bo.getParagraphId());
        lqw.eq(bo.getQuestionId() != null, KbQuestionParagraph::getQuestionId, bo.getQuestionId());
        return lqw;
    }

    /**
     * 新增问题段落
     *
     * @param bo 问题段落
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(KbQuestionParagraphBo bo) {
        KbQuestionParagraph add = MapstructUtils.convert(bo, KbQuestionParagraph.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改问题段落
     *
     * @param bo 问题段落
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(KbQuestionParagraphBo bo) {
        KbQuestionParagraph update = MapstructUtils.convert(bo, KbQuestionParagraph.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbQuestionParagraph entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除问题段落信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
