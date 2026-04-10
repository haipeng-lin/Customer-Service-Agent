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
import com.csagent.knowledge.domain.vo.KbDatasetVo;
import com.csagent.knowledge.domain.bo.KbDatasetBo;
import com.csagent.knowledge.service.IKbDatasetService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 知识库
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/knowledge/dataset")
public class KbDatasetController extends BaseController {

    private final IKbDatasetService kbDatasetService;

    /**
     * 查询知识库列表
     */
    @SaCheckPermission("knowledge:dataset:list")
    @GetMapping("/list")
    public TableDataInfo<KbDatasetVo> list(KbDatasetBo bo, PageQuery pageQuery) {
        return kbDatasetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库列表
     */
    @SaCheckPermission("knowledge:dataset:export")
    @Log(title = "知识库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(KbDatasetBo bo, HttpServletResponse response) {
        List<KbDatasetVo> list = kbDatasetService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库", KbDatasetVo.class, response);
    }

    /**
     * 获取知识库详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("knowledge:dataset:query")
    @GetMapping("/{id}")
    public R<KbDatasetVo> getInfo(@NotNull(message = "主键不能为空")
                                  @PathVariable Long id) {
        return R.ok(kbDatasetService.queryById(id));
    }

    /**
     * 新增知识库
     */
    @SaCheckPermission("knowledge:dataset:add")
    @Log(title = "知识库", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbDatasetBo bo) {
        return toAjax(kbDatasetService.insertByBo(bo));
    }

    /**
     * 修改知识库
     */
    @SaCheckPermission("knowledge:dataset:edit")
    @Log(title = "知识库", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbDatasetBo bo) {
        return toAjax(kbDatasetService.updateByBo(bo));
    }

    /**
     * 删除知识库
     *
     * @param ids 主键串
     */
    @SaCheckPermission("knowledge:dataset:remove")
    @Log(title = "知识库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kbDatasetService.deleteWithValidByIds(List.of(ids), true));
    }
}
