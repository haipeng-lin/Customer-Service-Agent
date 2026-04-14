package com.csagent.application.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.csagent.common.idempotent.annotation.RepeatSubmit;
import com.csagent.common.log.annotation.Log;
import com.csagent.common.web.core.BaseController;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.csagent.common.core.domain.R;
import com.csagent.common.core.validate.AddGroup;
import com.csagent.common.core.validate.EditGroup;
import com.csagent.common.log.enums.BusinessType;
import com.csagent.common.excel.utils.ExcelUtil;
import com.csagent.application.domain.vo.AppApplicationVo;
import com.csagent.application.domain.bo.AppApplicationBo;
import com.csagent.application.service.IAppApplicationService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 应用
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/application/application")
public class AppApplicationController extends BaseController {

    private final IAppApplicationService appApplicationService;

    /**
     * 查询应用列表
     */
    @SaCheckPermission("application:application:list")
    @GetMapping("/list")
    public TableDataInfo<AppApplicationVo> list(AppApplicationBo bo, PageQuery pageQuery) {
        return appApplicationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出应用列表
     */
    @SaCheckPermission("application:application:export")
    @Log(title = "应用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppApplicationBo bo, HttpServletResponse response) {
        List<AppApplicationVo> list = appApplicationService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用", AppApplicationVo.class, response);
    }

    /**
     * 获取应用详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("application:application:query")
    @GetMapping("/{id}")
    public R<AppApplicationVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long id) {
        return R.ok(appApplicationService.queryById(id));
    }

    /**
     * 新增应用
     */
    @SaCheckPermission("application:application:add")
    @Log(title = "应用", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppApplicationBo bo) {
        return toAjax(appApplicationService.insertByBo(bo));
    }

    /**
     * 修改应用
     */
    @SaCheckPermission("application:application:edit")
    @Log(title = "应用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppApplicationBo bo) {
        return toAjax(appApplicationService.updateByBo(bo));
    }

    /**
     * 删除应用
     *
     * @param ids 主键串
     */
    @SaCheckPermission("application:application:remove")
    @Log(title = "应用", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appApplicationService.deleteWithValidByIds(List.of(ids), true));
    }
}
