package com.csagent.knowledge.service.impl;

import com.csagent.common.core.utils.MapstructUtils;
import com.csagent.common.core.utils.StringUtils;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.csagent.knowledge.domain.bo.KbQuestionBo;
import com.csagent.knowledge.domain.vo.KbQuestionVo;
import com.csagent.knowledge.domain.KbQuestion;
import com.csagent.knowledge.mapper.KbQuestionMapper;
import com.csagent.knowledge.service.IKbQuestionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 问题Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KbQuestionServiceImpl implements IKbQuestionService {

    private final KbQuestionMapper baseMapper;

    /**
     * 查询问题
     *
     * @param id 主键
     * @return 问题
     */
    @Override
    public KbQuestionVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询问题列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 问题分页列表
     */
    @Override
    public TableDataInfo<KbQuestionVo> queryPageList(KbQuestionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<KbQuestion> lqw = buildQueryWrapper(bo);
        Page<KbQuestionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的问题列表
     *
     * @param bo 查询条件
     * @return 问题列表
     */
    @Override
    public List<KbQuestionVo> queryList(KbQuestionBo bo) {
        LambdaQueryWrapper<KbQuestion> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbQuestion> buildQueryWrapper(KbQuestionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KbQuestion> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(KbQuestion::getId);
        lqw.eq(bo.getDatasetId() != null, KbQuestion::getDatasetId, bo.getDatasetId());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), KbQuestion::getContent, bo.getContent());
        lqw.eq(bo.getHitNum() != null, KbQuestion::getHitNum, bo.getHitNum());
        return lqw;
    }

    /**
     * 新增问题
     *
     * @param bo 问题
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(KbQuestionBo bo) {
        KbQuestion add = MapstructUtils.convert(bo, KbQuestion.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改问题
     *
     * @param bo 问题
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(KbQuestionBo bo) {
        KbQuestion update = MapstructUtils.convert(bo, KbQuestion.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbQuestion entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除问题信息
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
