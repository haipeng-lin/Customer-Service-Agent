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
import com.csagent.knowledge.domain.bo.KbDatasetBo;
import com.csagent.knowledge.domain.vo.KbDatasetVo;
import com.csagent.knowledge.domain.KbDataset;
import com.csagent.knowledge.mapper.KbDatasetMapper;
import com.csagent.knowledge.service.IKbDatasetService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 知识库Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KbDatasetServiceImpl implements IKbDatasetService {

    private final KbDatasetMapper baseMapper;

    /**
     * 查询知识库
     *
     * @param id 主键
     * @return 知识库
     */
    @Override
    public KbDatasetVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询知识库列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 知识库分页列表
     */
    @Override
    public TableDataInfo<KbDatasetVo> queryPageList(KbDatasetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<KbDataset> lqw = buildQueryWrapper(bo);
        Page<KbDatasetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的知识库列表
     *
     * @param bo 查询条件
     * @return 知识库列表
     */
    @Override
    public List<KbDatasetVo> queryList(KbDatasetBo bo) {
        return baseMapper.selectDatasetVoWithCount();
    }

    private LambdaQueryWrapper<KbDataset> buildQueryWrapper(KbDatasetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KbDataset> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(KbDataset::getId);
        lqw.eq(bo.getEmbeddingModelId() != null, KbDataset::getEmbeddingModelId, bo.getEmbeddingModelId());
        lqw.like(StringUtils.isNotBlank(bo.getEmbeddingModelName()), KbDataset::getEmbeddingModelName, bo.getEmbeddingModelName());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), KbDataset::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), KbDataset::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbDataset::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增知识库
     *
     * @param bo 知识库
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(KbDatasetBo bo) {
        KbDataset add = MapstructUtils.convert(bo, KbDataset.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改知识库
     *
     * @param bo 知识库
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(KbDatasetBo bo) {
        KbDataset update = MapstructUtils.convert(bo, KbDataset.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbDataset entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除知识库信息
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
            new LambdaUpdateWrapper<KbDataset>()
                .set(KbDataset::getStatus, status)
                .eq(KbDataset::getId, id));
    }
}
