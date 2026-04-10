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
import com.csagent.knowledge.domain.vo.KbQuestionVo;
import com.csagent.knowledge.domain.bo.KbQuestionBo;
import com.csagent.knowledge.service.IKbQuestionService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 问题
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/knowledge/question")
public class KbQuestionController extends BaseController {

    private final IKbQuestionService kbQuestionService;

    /**
     * 查询问题列表
     */
    @SaCheckPermission("knowledge:question:list")
    @GetMapping("/list")
    public TableDataInfo<KbQuestionVo> list(KbQuestionBo bo, PageQuery pageQuery) {
        return kbQuestionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出问题列表
     */
    @SaCheckPermission("knowledge:question:export")
    @Log(title = "问题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(KbQuestionBo bo, HttpServletResponse response) {
        List<KbQuestionVo> list = kbQuestionService.queryList(bo);
        ExcelUtil.exportExcel(list, "问题", KbQuestionVo.class, response);
    }

    /**
     * 获取问题详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("knowledge:question:query")
    @GetMapping("/{id}")
    public R<KbQuestionVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable Long id) {
        return R.ok(kbQuestionService.queryById(id));
    }

    /**
     * 新增问题
     */
    @SaCheckPermission("knowledge:question:add")
    @Log(title = "问题", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbQuestionBo bo) {
        return toAjax(kbQuestionService.insertByBo(bo));
    }

    /**
     * 修改问题
     */
    @SaCheckPermission("knowledge:question:edit")
    @Log(title = "问题", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbQuestionBo bo) {
        return toAjax(kbQuestionService.updateByBo(bo));
    }

    /**
     * 删除问题
     *
     * @param ids 主键串
     */
    @SaCheckPermission("knowledge:question:remove")
    @Log(title = "问题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kbQuestionService.deleteWithValidByIds(List.of(ids), true));
    }
}
