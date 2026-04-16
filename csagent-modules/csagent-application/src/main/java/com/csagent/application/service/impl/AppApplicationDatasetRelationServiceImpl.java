package com.csagent.application.service.impl;

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
import com.csagent.application.domain.bo.AppApplicationDatasetRelationBo;
import com.csagent.application.domain.vo.AppApplicationDatasetRelationVo;
import com.csagent.application.domain.AppApplicationDatasetRelation;
import com.csagent.application.mapper.AppApplicationDatasetRelationMapper;
import com.csagent.application.service.IAppApplicationDatasetRelationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用知识库关联Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-16
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AppApplicationDatasetRelationServiceImpl implements IAppApplicationDatasetRelationService {

    private final AppApplicationDatasetRelationMapper baseMapper;

    /**
     * 查询应用知识库关联
     *
     * @param applicationId 主键
     * @return 应用知识库关联
     */
    @Override
    public AppApplicationDatasetRelationVo queryById(Long applicationId) {
        return baseMapper.selectVoById(applicationId);
    }

    /**
     * 分页查询应用知识库关联列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 应用知识库关联分页列表
     */
    @Override
    public TableDataInfo<AppApplicationDatasetRelationVo> queryPageList(AppApplicationDatasetRelationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppApplicationDatasetRelation> lqw = buildQueryWrapper(bo);
        Page<AppApplicationDatasetRelationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的应用知识库关联列表
     *
     * @param bo 查询条件
     * @return 应用知识库关联列表
     */
    @Override
    public List<AppApplicationDatasetRelationVo> queryList(AppApplicationDatasetRelationBo bo) {
        LambdaQueryWrapper<AppApplicationDatasetRelation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppApplicationDatasetRelation> buildQueryWrapper(AppApplicationDatasetRelationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppApplicationDatasetRelation> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(AppApplicationDatasetRelation::getApplicationId);
        lqw.eq(bo.getDatasetId() != null, AppApplicationDatasetRelation::getDatasetId, bo.getDatasetId());
        return lqw;
    }

    /**
     * 新增应用知识库关联
     *
     * @param bo 应用知识库关联
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppApplicationDatasetRelationBo bo) {
        AppApplicationDatasetRelation add = MapstructUtils.convert(bo, AppApplicationDatasetRelation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setApplicationId(add.getApplicationId());
        }
        return flag;
    }

    /**
     * 修改应用知识库关联
     *
     * @param bo 应用知识库关联
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppApplicationDatasetRelationBo bo) {
        AppApplicationDatasetRelation update = MapstructUtils.convert(bo, AppApplicationDatasetRelation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppApplicationDatasetRelation entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除应用知识库关联信息
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
