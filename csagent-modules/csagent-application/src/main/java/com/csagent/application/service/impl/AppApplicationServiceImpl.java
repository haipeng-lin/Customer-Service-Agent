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
import com.csagent.application.domain.bo.AppApplicationBo;
import com.csagent.application.domain.vo.AppApplicationVo;
import com.csagent.application.domain.AppApplication;
import com.csagent.application.mapper.AppApplicationMapper;
import com.csagent.application.service.IAppApplicationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AppApplicationServiceImpl implements IAppApplicationService {

    private final AppApplicationMapper baseMapper;

    /**
     * 查询应用
     *
     * @param id 主键
     * @return 应用
     */
    @Override
    public AppApplicationVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询应用列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 应用分页列表
     */
    @Override
    public TableDataInfo<AppApplicationVo> queryPageList(AppApplicationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppApplication> lqw = buildQueryWrapper(bo);
        Page<AppApplicationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的应用列表
     *
     * @param bo 查询条件
     * @return 应用列表
     */
    @Override
    public List<AppApplicationVo> queryList(AppApplicationBo bo) {
        LambdaQueryWrapper<AppApplication> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppApplication> buildQueryWrapper(AppApplicationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppApplication> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(AppApplication::getId);
        lqw.like(StringUtils.isNotBlank(bo.getName()), AppApplication::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getModelId()), AppApplication::getModelId, bo.getModelId());
        lqw.like(StringUtils.isNotBlank(bo.getModelName()), AppApplication::getModelName, bo.getModelName());
        lqw.eq(StringUtils.isNotBlank(bo.getIsRelateDataset()), AppApplication::getIsRelateDataset, bo.getIsRelateDataset());
        lqw.eq(StringUtils.isNotBlank(bo.getSearchMode()), AppApplication::getSearchMode, bo.getSearchMode());
        lqw.eq(bo.getRerankModelId() != null, AppApplication::getRerankModelId, bo.getRerankModelId());
        lqw.eq(StringUtils.isNotBlank(bo.getIsShowRelation()), AppApplication::getIsShowRelation, bo.getIsShowRelation());
        lqw.eq(StringUtils.isNotBlank(bo.getIsShowTime()), AppApplication::getIsShowTime, bo.getIsShowTime());
        lqw.eq(StringUtils.isNotBlank(bo.getIsShowToken()), AppApplication::getIsShowToken, bo.getIsShowToken());
        lqw.eq(StringUtils.isNotBlank(bo.getIsShowAppraise()), AppApplication::getIsShowAppraise, bo.getIsShowAppraise());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), AppApplication::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增应用
     *
     * @param bo 应用
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppApplicationBo bo) {
        AppApplication add = MapstructUtils.convert(bo, AppApplication.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改应用
     *
     * @param bo 应用
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppApplicationBo bo) {
        AppApplication update = MapstructUtils.convert(bo, AppApplication.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppApplication entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除应用信息
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
