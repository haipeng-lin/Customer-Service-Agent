package com.csagent.knowledge.controller;

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
import com.csagent.knowledge.domain.vo.KbDocumentVo;
import com.csagent.knowledge.domain.bo.KbDocumentBo;
import com.csagent.knowledge.service.IKbDocumentService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 文档
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/knowledge/document")
public class KbDocumentController extends BaseController {

    private final IKbDocumentService kbDocumentService;

    /**
     * 查询全部文档
     */
    @SaCheckPermission("knowledge:document:list")
    @GetMapping("/listAll")
    public R<List<KbDocumentVo>> listAll(KbDocumentBo bo) {
        return R.ok(kbDocumentService.queryList(bo));
    }

    /**
     * 查询文档列表
     */
    @SaCheckPermission("knowledge:document:list")
    @GetMapping("/list")
    public TableDataInfo<KbDocumentVo> list(KbDocumentBo bo, PageQuery pageQuery) {
        return kbDocumentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文档列表
     */
    @SaCheckPermission("knowledge:document:export")
    @Log(title = "文档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(KbDocumentBo bo, HttpServletResponse response) {
        List<KbDocumentVo> list = kbDocumentService.queryList(bo);
        ExcelUtil.exportExcel(list, "文档", KbDocumentVo.class, response);
    }

    /**
     * 获取文档详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("knowledge:document:query")
    @GetMapping("/{id}")
    public R<KbDocumentVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable Long id) {
        return R.ok(kbDocumentService.queryById(id));
    }

    /**
     * 新增文档
     */
    @SaCheckPermission("knowledge:document:add")
    @Log(title = "文档", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbDocumentBo bo) {
        return toAjax(kbDocumentService.insertByBo(bo));
    }

    /**
     * 修改文档
     */
    @SaCheckPermission("knowledge:document:edit")
    @Log(title = "文档", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbDocumentBo bo) {
        return toAjax(kbDocumentService.updateByBo(bo));
    }

    /**
     * 删除文档
     *
     * @param ids 主键串
     */
    @SaCheckPermission("knowledge:document:remove")
    @Log(title = "文档", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kbDocumentService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("knowledge:document:edit")
    @Log(title = "文档", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody KbDocumentBo model) {
        return toAjax(kbDocumentService.updateStatus(model.getId(), model.getStatus()));
    }
}
