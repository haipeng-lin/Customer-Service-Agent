package com.csagent.model.service.impl;

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
import com.csagent.model.domain.bo.MdModelTokenBo;
import com.csagent.model.domain.vo.MdModelTokenVo;
import com.csagent.model.domain.MdModelToken;
import com.csagent.model.mapper.MdModelTokenMapper;
import com.csagent.model.service.IMdModelTokenService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 模型tokenService业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-15
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MdModelTokenServiceImpl implements IMdModelTokenService {

    private final MdModelTokenMapper baseMapper;

    /**
     * 查询模型token
     *
     * @param id 主键
     * @return 模型token
     */
    @Override
    public MdModelTokenVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询模型token列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 模型token分页列表
     */
    @Override
    public TableDataInfo<MdModelTokenVo> queryPageList(MdModelTokenBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MdModelToken> lqw = buildQueryWrapper(bo);
        Page<MdModelTokenVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的模型token列表
     *
     * @param bo 查询条件
     * @return 模型token列表
     */
    @Override
    public List<MdModelTokenVo> queryList(MdModelTokenBo bo) {
        LambdaQueryWrapper<MdModelToken> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MdModelToken> buildQueryWrapper(MdModelTokenBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MdModelToken> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(MdModelToken::getId);
        lqw.eq(bo.getModelId() != null, MdModelToken::getModelId, bo.getModelId());
        lqw.eq(StringUtils.isNotBlank(bo.getSource()), MdModelToken::getSource, bo.getSource());
        lqw.eq(bo.getUserId() != null, MdModelToken::getUserId, bo.getUserId());
        lqw.eq(bo.getApplicationId() != null, MdModelToken::getApplicationId, bo.getApplicationId());
        return lqw;
    }

    /**
     * 新增模型token
     *
     * @param bo 模型token
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MdModelTokenBo bo) {
        MdModelToken add = MapstructUtils.convert(bo, MdModelToken.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改模型token
     *
     * @param bo 模型token
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MdModelTokenBo bo) {
        MdModelToken update = MapstructUtils.convert(bo, MdModelToken.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MdModelToken entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除模型token信息
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
