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
import com.csagent.application.domain.bo.AppChatSessionBo;
import com.csagent.application.domain.vo.AppChatSessionVo;
import com.csagent.application.domain.AppChatSession;
import com.csagent.application.mapper.AppChatSessionMapper;
import com.csagent.application.service.IAppChatSessionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 对话会话Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AppChatSessionServiceImpl implements IAppChatSessionService {

    private final AppChatSessionMapper baseMapper;

    /**
     * 查询对话会话
     *
     * @param id 主键
     * @return 对话会话
     */
    @Override
    public AppChatSessionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询对话会话列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 对话会话分页列表
     */
    @Override
    public TableDataInfo<AppChatSessionVo> queryPageList(AppChatSessionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppChatSession> lqw = buildQueryWrapper(bo);
        Page<AppChatSessionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的对话会话列表
     *
     * @param bo 查询条件
     * @return 对话会话列表
     */
    @Override
    public List<AppChatSessionVo> queryList(AppChatSessionBo bo) {
        LambdaQueryWrapper<AppChatSession> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppChatSession> buildQueryWrapper(AppChatSessionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppChatSession> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(AppChatSession::getId);
        lqw.eq(bo.getUserId() != null, AppChatSession::getUserId, bo.getUserId());
        lqw.eq(bo.getApplicationId() != null, AppChatSession::getApplicationId, bo.getApplicationId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), AppChatSession::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getSource()), AppChatSession::getSource, bo.getSource());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), AppChatSession::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增对话会话
     *
     * @param bo 对话会话
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppChatSessionBo bo) {
        AppChatSession add = MapstructUtils.convert(bo, AppChatSession.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改对话会话
     *
     * @param bo 对话会话
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppChatSessionBo bo) {
        AppChatSession update = MapstructUtils.convert(bo, AppChatSession.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppChatSession entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除对话会话信息
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
