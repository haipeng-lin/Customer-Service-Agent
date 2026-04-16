package com.csagent.application.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.csagent.application.domain.bo.AppApplicationDatasetRelationBo;
import com.csagent.application.domain.vo.AppApplicationDatasetRelationVo;
import com.csagent.application.service.IAppApplicationDatasetRelationService;
import com.csagent.common.core.domain.R;
import com.csagent.common.core.validate.AddGroup;
import com.csagent.common.core.validate.EditGroup;
import com.csagent.common.excel.utils.ExcelUtil;
import com.csagent.common.idempotent.annotation.RepeatSubmit;
import com.csagent.common.log.annotation.Log;
import com.csagent.common.log.enums.BusinessType;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.web.core.BaseController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用知识库关联
 *
 * @author haipeng-lin
 * @date 2026-04-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/application/applicationDatasetRelation")
public class AppApplicationDatasetRelationController extends BaseController {

    private final IAppApplicationDatasetRelationService appApplicationDatasetRelationService;

    /**
     * 查询应用知识库关联列表
     */
    @SaCheckPermission("application:applicationDatasetRelation:list")
    @GetMapping("/list")
    public TableDataInfo<AppApplicationDatasetRelationVo> list(AppApplicationDatasetRelationBo bo, PageQuery pageQuery) {
        return appApplicationDatasetRelationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出应用知识库关联列表
     */
    @SaCheckPermission("application:applicationDatasetRelation:export")
    @Log(title = "应用知识库关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppApplicationDatasetRelationBo bo, HttpServletResponse response) {
        List<AppApplicationDatasetRelationVo> list = appApplicationDatasetRelationService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用知识库关联", AppApplicationDatasetRelationVo.class, response);
    }

    /**
     * 获取应用知识库关联详细信息
     *
     * @param applicationId 主键
     */
    @SaCheckPermission("application:applicationDatasetRelation:query")
    @GetMapping("/{applicationId}")
    public R<AppApplicationDatasetRelationVo> getInfo(@NotNull(message = "主键不能为空")
                                                      @PathVariable Long applicationId) {
        return R.ok(appApplicationDatasetRelationService.queryById(applicationId));
    }

    /**
     * 新增应用知识库关联
     */
    @SaCheckPermission("application:applicationDatasetRelation:add")
    @Log(title = "应用知识库关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppApplicationDatasetRelationBo bo) {
        return toAjax(appApplicationDatasetRelationService.insertByBo(bo));
    }

    /**
     * 修改应用知识库关联
     */
    @SaCheckPermission("application:applicationDatasetRelation:edit")
    @Log(title = "应用知识库关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppApplicationDatasetRelationBo bo) {
        return toAjax(appApplicationDatasetRelationService.updateByBo(bo));
    }

    /**
     * 删除应用知识库关联
     *
     * @param applicationIds 主键串
     */
    @SaCheckPermission("application:applicationDatasetRelation:remove")
    @Log(title = "应用知识库关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applicationIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] applicationIds) {
        return toAjax(appApplicationDatasetRelationService.deleteWithValidByIds(List.of(applicationIds), true));
    }
}
