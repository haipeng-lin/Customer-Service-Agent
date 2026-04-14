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
import com.csagent.application.domain.vo.AppChatMessageVo;
import com.csagent.application.domain.bo.AppChatMessageBo;
import com.csagent.application.service.IAppChatMessageService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 对话消息
 *
 * @author haipeng-lin
 * @date 2026-04-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/application/chatMessage")
public class AppChatMessageController extends BaseController {

    private final IAppChatMessageService appChatMessageService;

    /**
     * 查询对话消息列表
     */
    @SaCheckPermission("application:chatMessage:list")
    @GetMapping("/list")
    public TableDataInfo<AppChatMessageVo> list(AppChatMessageBo bo, PageQuery pageQuery) {
        return appChatMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出对话消息列表
     */
    @SaCheckPermission("application:chatMessage:export")
    @Log(title = "对话消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppChatMessageBo bo, HttpServletResponse response) {
        List<AppChatMessageVo> list = appChatMessageService.queryList(bo);
        ExcelUtil.exportExcel(list, "对话消息", AppChatMessageVo.class, response);
    }

    /**
     * 获取对话消息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("application:chatMessage:query")
    @GetMapping("/{id}")
    public R<AppChatMessageVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long id) {
        return R.ok(appChatMessageService.queryById(id));
    }

    /**
     * 新增对话消息
     */
    @SaCheckPermission("application:chatMessage:add")
    @Log(title = "对话消息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppChatMessageBo bo) {
        return toAjax(appChatMessageService.insertByBo(bo));
    }

    /**
     * 修改对话消息
     */
    @SaCheckPermission("application:chatMessage:edit")
    @Log(title = "对话消息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppChatMessageBo bo) {
        return toAjax(appChatMessageService.updateByBo(bo));
    }

    /**
     * 删除对话消息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("application:chatMessage:remove")
    @Log(title = "对话消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appChatMessageService.deleteWithValidByIds(List.of(ids), true));
    }
}
