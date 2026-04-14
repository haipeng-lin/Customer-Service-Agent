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
import com.csagent.application.domain.vo.AppChatSessionVo;
import com.csagent.application.domain.bo.AppChatSessionBo;
import com.csagent.application.service.IAppChatSessionService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 对话会话
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/application/chatSession")
public class AppChatSessionController extends BaseController {

    private final IAppChatSessionService appChatSessionService;

    /**
     * 查询对话会话列表
     */
    @SaCheckPermission("application:chatSession:list")
    @GetMapping("/list")
    public TableDataInfo<AppChatSessionVo> list(AppChatSessionBo bo, PageQuery pageQuery) {
        return appChatSessionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出对话会话列表
     */
    @SaCheckPermission("application:chatSession:export")
    @Log(title = "对话会话", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppChatSessionBo bo, HttpServletResponse response) {
        List<AppChatSessionVo> list = appChatSessionService.queryList(bo);
        ExcelUtil.exportExcel(list, "对话会话", AppChatSessionVo.class, response);
    }

    /**
     * 获取对话会话详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("application:chatSession:query")
    @GetMapping("/{id}")
    public R<AppChatSessionVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long id) {
        return R.ok(appChatSessionService.queryById(id));
    }

    /**
     * 新增对话会话
     */
    @SaCheckPermission("application:chatSession:add")
    @Log(title = "对话会话", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppChatSessionBo bo) {
        return toAjax(appChatSessionService.insertByBo(bo));
    }

    /**
     * 修改对话会话
     */
    @SaCheckPermission("application:chatSession:edit")
    @Log(title = "对话会话", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppChatSessionBo bo) {
        return toAjax(appChatSessionService.updateByBo(bo));
    }

    /**
     * 删除对话会话
     *
     * @param ids 主键串
     */
    @SaCheckPermission("application:chatSession:remove")
    @Log(title = "对话会话", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appChatSessionService.deleteWithValidByIds(List.of(ids), true));
    }
}
