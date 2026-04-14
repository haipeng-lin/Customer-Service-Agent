package com.csagent.application.service;

import com.csagent.application.domain.vo.AppChatMessageVo;
import com.csagent.application.domain.bo.AppChatMessageBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 对话消息Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
public interface IAppChatMessageService {

    /**
     * 查询对话消息
     *
     * @param id 主键
     * @return 对话消息
     */
    AppChatMessageVo queryById(Long id);

    /**
     * 分页查询对话消息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 对话消息分页列表
     */
    TableDataInfo<AppChatMessageVo> queryPageList(AppChatMessageBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的对话消息列表
     *
     * @param bo 查询条件
     * @return 对话消息列表
     */
    List<AppChatMessageVo> queryList(AppChatMessageBo bo);

    /**
     * 新增对话消息
     *
     * @param bo 对话消息
     * @return 是否新增成功
     */
    Boolean insertByBo(AppChatMessageBo bo);

    /**
     * 修改对话消息
     *
     * @param bo 对话消息
     * @return 是否修改成功
     */
    Boolean updateByBo(AppChatMessageBo bo);

    /**
     * 校验并批量删除对话消息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
