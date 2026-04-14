package com.csagent.application.service;

import com.csagent.application.domain.vo.AppChatSessionVo;
import com.csagent.application.domain.bo.AppChatSessionBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 对话会话Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
public interface IAppChatSessionService {

    /**
     * 查询对话会话
     *
     * @param id 主键
     * @return 对话会话
     */
    AppChatSessionVo queryById(Long id);

    /**
     * 分页查询对话会话列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 对话会话分页列表
     */
    TableDataInfo<AppChatSessionVo> queryPageList(AppChatSessionBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的对话会话列表
     *
     * @param bo 查询条件
     * @return 对话会话列表
     */
    List<AppChatSessionVo> queryList(AppChatSessionBo bo);

    /**
     * 新增对话会话
     *
     * @param bo 对话会话
     * @return 是否新增成功
     */
    Boolean insertByBo(AppChatSessionBo bo);

    /**
     * 修改对话会话
     *
     * @param bo 对话会话
     * @return 是否修改成功
     */
    Boolean updateByBo(AppChatSessionBo bo);

    /**
     * 校验并批量删除对话会话信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
