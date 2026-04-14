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
import com.csagent.application.domain.bo.AppChatMessageBo;
import com.csagent.application.domain.vo.AppChatMessageVo;
import com.csagent.application.domain.AppChatMessage;
import com.csagent.application.mapper.AppChatMessageMapper;
import com.csagent.application.service.IAppChatMessageService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 对话消息Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AppChatMessageServiceImpl implements IAppChatMessageService {

    private final AppChatMessageMapper baseMapper;

    /**
     * 查询对话消息
     *
     * @param id 主键
     * @return 对话消息
     */
    @Override
    public AppChatMessageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询对话消息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 对话消息分页列表
     */
    @Override
    public TableDataInfo<AppChatMessageVo> queryPageList(AppChatMessageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppChatMessage> lqw = buildQueryWrapper(bo);
        Page<AppChatMessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的对话消息列表
     *
     * @param bo 查询条件
     * @return 对话消息列表
     */
    @Override
    public List<AppChatMessageVo> queryList(AppChatMessageBo bo) {
        LambdaQueryWrapper<AppChatMessage> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppChatMessage> buildQueryWrapper(AppChatMessageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppChatMessage> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(AppChatMessage::getId);
        lqw.eq(bo.getUserId() != null, AppChatMessage::getUserId, bo.getUserId());
        lqw.eq(bo.getApplicationId() != null, AppChatMessage::getApplicationId, bo.getApplicationId());
        lqw.eq(bo.getSessionId() != null, AppChatMessage::getSessionId, bo.getSessionId());
        lqw.eq(StringUtils.isNotBlank(bo.getRole()), AppChatMessage::getRole, bo.getRole());
        lqw.eq(StringUtils.isNotBlank(bo.getFeedback()), AppChatMessage::getFeedback, bo.getFeedback());
        return lqw;
    }

    /**
     * 新增对话消息
     *
     * @param bo 对话消息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppChatMessageBo bo) {
        AppChatMessage add = MapstructUtils.convert(bo, AppChatMessage.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改对话消息
     *
     * @param bo 对话消息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppChatMessageBo bo) {
        AppChatMessage update = MapstructUtils.convert(bo, AppChatMessage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppChatMessage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除对话消息信息
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
