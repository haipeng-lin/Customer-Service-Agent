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
import com.csagent.knowledge.domain.vo.KbQuestionParagraphVo;
import com.csagent.knowledge.domain.bo.KbQuestionParagraphBo;
import com.csagent.knowledge.service.IKbQuestionParagraphService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 问题段落
 *
 * @author Lion Li
 * @date 2026-04-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/knowledge/questionParagraph")
public class KbQuestionParagraphController extends BaseController {

    private final IKbQuestionParagraphService kbQuestionParagraphService;

    /**
     * 查询问题段落列表
     */
    @SaCheckPermission("knowledge:questionParagraph:list")
    @GetMapping("/list")
    public TableDataInfo<KbQuestionParagraphVo> list(KbQuestionParagraphBo bo, PageQuery pageQuery) {
        return kbQuestionParagraphService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出问题段落列表
     */
    @SaCheckPermission("knowledge:questionParagraph:export")
    @Log(title = "问题段落", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(KbQuestionParagraphBo bo, HttpServletResponse response) {
        List<KbQuestionParagraphVo> list = kbQuestionParagraphService.queryList(bo);
        ExcelUtil.exportExcel(list, "问题段落", KbQuestionParagraphVo.class, response);
    }

    /**
     * 获取问题段落详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("knowledge:questionParagraph:query")
    @GetMapping("/{id}")
    public R<KbQuestionParagraphVo> getInfo(@NotNull(message = "主键不能为空")
                                            @PathVariable Long id) {
        return R.ok(kbQuestionParagraphService.queryById(id));
    }

    /**
     * 新增问题段落
     */
    @SaCheckPermission("knowledge:questionParagraph:add")
    @Log(title = "问题段落", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbQuestionParagraphBo bo) {
        return toAjax(kbQuestionParagraphService.insertByBo(bo));
    }

    /**
     * 修改问题段落
     */
    @SaCheckPermission("knowledge:questionParagraph:edit")
    @Log(title = "问题段落", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbQuestionParagraphBo bo) {
        return toAjax(kbQuestionParagraphService.updateByBo(bo));
    }

    /**
     * 删除问题段落
     *
     * @param ids 主键串
     */
    @SaCheckPermission("knowledge:questionParagraph:remove")
    @Log(title = "问题段落", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kbQuestionParagraphService.deleteWithValidByIds(List.of(ids), true));
    }
}
