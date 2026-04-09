package com.csagent.model.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.model.domain.bo.MdModelBo;
import org.dromara.model.domain.vo.MdModelVo;
import org.dromara.model.domain.MdModel;
import org.dromara.model.mapper.MdModelMapper;
import org.dromara.model.service.IMdModelService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 模型Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MdModelServiceImpl implements IMdModelService {

    private final MdModelMapper baseMapper;

    /**
     * 查询模型
     *
     * @param id 主键
     * @return 模型
     */
    @Override
    public MdModelVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询模型列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 模型分页列表
     */
    @Override
    public TableDataInfo<MdModelVo> queryPageList(MdModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MdModel> lqw = buildQueryWrapper(bo);
        Page<MdModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的模型列表
     *
     * @param bo 查询条件
     * @return 模型列表
     */
    @Override
    public List<MdModelVo> queryList(MdModelBo bo) {
        LambdaQueryWrapper<MdModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MdModel> buildQueryWrapper(MdModelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MdModel> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(MdModel::getId);
        lqw.like(StringUtils.isNotBlank(bo.getName()), MdModel::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), MdModel::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getFlag()), MdModel::getFlag, bo.getFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), MdModel::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getKey()), MdModel::getKey, bo.getKey());
        lqw.eq(StringUtils.isNotBlank(bo.getModels()), MdModel::getModels, bo.getModels());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), MdModel::getIcon, bo.getIcon());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), MdModel::getStatus, bo.getStatus());
        lqw.eq(bo.getUpdateDept() != null, MdModel::getUpdateDept, bo.getUpdateDept());
        return lqw;
    }

    /**
     * 新增模型
     *
     * @param bo 模型
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MdModelBo bo) {
        MdModel add = MapstructUtils.convert(bo, MdModel.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改模型
     *
     * @param bo 模型
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MdModelBo bo) {
        MdModel update = MapstructUtils.convert(bo, MdModel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MdModel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除模型信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
