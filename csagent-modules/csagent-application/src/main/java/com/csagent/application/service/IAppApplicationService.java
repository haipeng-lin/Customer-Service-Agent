package com.csagent.application.service;

import com.csagent.application.domain.vo.AppApplicationVo;
import com.csagent.application.domain.bo.AppApplicationBo;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 应用Service接口
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
public interface IAppApplicationService {

    /**
     * 查询应用
     *
     * @param id 主键
     * @return 应用
     */
    AppApplicationVo queryById(Long id);

    /**
     * 分页查询应用列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 应用分页列表
     */
    TableDataInfo<AppApplicationVo> queryPageList(AppApplicationBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的应用列表
     *
     * @param bo 查询条件
     * @return 应用列表
     */
    List<AppApplicationVo> queryList(AppApplicationBo bo);

    /**
     * 新增应用
     *
     * @param bo 应用
     * @return 是否新增成功
     */
    Boolean insertByBo(AppApplicationBo bo);

    /**
     * 修改应用
     *
     * @param bo 应用
     * @return 是否修改成功
     */
    Boolean updateByBo(AppApplicationBo bo);

    /**
     * 校验并批量删除应用信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
