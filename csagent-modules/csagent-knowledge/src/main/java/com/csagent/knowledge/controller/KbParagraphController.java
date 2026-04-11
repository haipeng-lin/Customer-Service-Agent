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
import com.csagent.knowledge.domain.vo.KbParagraphVo;
import com.csagent.knowledge.domain.bo.KbParagraphBo;
import com.csagent.knowledge.service.IKbParagraphService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 段落
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/knowledge/paragraph")
public class KbParagraphController extends BaseController {

    private final IKbParagraphService kbParagraphService;

    /**
     * 查询全部段落
     */
    @SaCheckPermission("knowledge:paragraph:list")
    @GetMapping("/listAll")
    public R<List<KbParagraphVo>> listAll(KbParagraphBo bo) {
        return R.ok(kbParagraphService.queryList(bo));
    }

    /**
     * 查询段落列表
     */
    @SaCheckPermission("knowledge:paragraph:list")
    @GetMapping("/list")
    public TableDataInfo<KbParagraphVo> list(KbParagraphBo bo, PageQuery pageQuery) {
        return kbParagraphService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出段落列表
     */
    @SaCheckPermission("knowledge:paragraph:export")
    @Log(title = "段落", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(KbParagraphBo bo, HttpServletResponse response) {
        List<KbParagraphVo> list = kbParagraphService.queryList(bo);
        ExcelUtil.exportExcel(list, "段落", KbParagraphVo.class, response);
    }

    /**
     * 获取段落详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("knowledge:paragraph:query")
    @GetMapping("/{id}")
    public R<KbParagraphVo> getInfo(@NotNull(message = "主键不能为空")
                                    @PathVariable Long id) {
        return R.ok(kbParagraphService.queryById(id));
    }

    /**
     * 新增段落
     */
    @SaCheckPermission("knowledge:paragraph:add")
    @Log(title = "段落", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbParagraphBo bo) {
        return toAjax(kbParagraphService.insertByBo(bo));
    }

    /**
     * 修改段落
     */
    @SaCheckPermission("knowledge:paragraph:edit")
    @Log(title = "段落", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbParagraphBo bo) {
        return toAjax(kbParagraphService.updateByBo(bo));
    }

    /**
     * 删除段落
     *
     * @param ids 主键串
     */
    @SaCheckPermission("knowledge:paragraph:remove")
    @Log(title = "段落", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kbParagraphService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("knowledge:paragraph:edit")
    @Log(title = "段落", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody KbParagraphBo model) {
        return toAjax(kbParagraphService.updateStatus(model.getId(), model.getStatus()));
    }
}
