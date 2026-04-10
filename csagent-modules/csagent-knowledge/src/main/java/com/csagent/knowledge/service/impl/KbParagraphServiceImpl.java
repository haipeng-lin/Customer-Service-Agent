package com.csagent.knowledge.service.impl;

import com.csagent.common.core.utils.MapstructUtils;
import com.csagent.common.core.utils.StringUtils;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.csagent.knowledge.domain.bo.KbParagraphBo;
import com.csagent.knowledge.domain.vo.KbParagraphVo;
import com.csagent.knowledge.domain.KbParagraph;
import com.csagent.knowledge.mapper.KbParagraphMapper;
import com.csagent.knowledge.service.IKbParagraphService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 段落Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KbParagraphServiceImpl implements IKbParagraphService {

    private final KbParagraphMapper baseMapper;

    /**
     * 查询段落
     *
     * @param id 主键
     * @return 段落
     */
    @Override
    public KbParagraphVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询段落列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 段落分页列表
     */
    @Override
    public TableDataInfo<KbParagraphVo> queryPageList(KbParagraphBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<KbParagraph> lqw = buildQueryWrapper(bo);
        Page<KbParagraphVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的段落列表
     *
     * @param bo 查询条件
     * @return 段落列表
     */
    @Override
    public List<KbParagraphVo> queryList(KbParagraphBo bo) {
        LambdaQueryWrapper<KbParagraph> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbParagraph> buildQueryWrapper(KbParagraphBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KbParagraph> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(KbParagraph::getId);
        lqw.eq(bo.getDatasetId() != null, KbParagraph::getDatasetId, bo.getDatasetId());
        lqw.eq(bo.getDocumentId() != null, KbParagraph::getDocumentId, bo.getDocumentId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), KbParagraph::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), KbParagraph::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getIndexStatus()), KbParagraph::getIndexStatus, bo.getIndexStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbParagraph::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增段落
     *
     * @param bo 段落
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(KbParagraphBo bo) {
        KbParagraph add = MapstructUtils.convert(bo, KbParagraph.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改段落
     *
     * @param bo 段落
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(KbParagraphBo bo) {
        KbParagraph update = MapstructUtils.convert(bo, KbParagraph.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbParagraph entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除段落信息
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

    @Override
    public int updateStatus(Long id, String status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<KbParagraph>()
                .set(KbParagraph::getStatus, status)
                .eq(KbParagraph::getId, id));
    }
}
