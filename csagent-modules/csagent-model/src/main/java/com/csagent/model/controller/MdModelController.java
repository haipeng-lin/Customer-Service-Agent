package com.csagent.model.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.csagent.model.domain.vo.MdModelVo;
import com.csagent.model.domain.bo.MdModelBo;
import com.csagent.model.service.IMdModelService;
import com.csagent.common.mybatis.core.page.TableDataInfo;

/**
 * 模型
 *
 * @author haipeng-lin
 * @date 2026-04-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/model/model")
public class MdModelController extends BaseController {

    @Autowired
    private final IMdModelService mdModelService;

    /**
     * 查询模型列表
     */
    @SaCheckPermission("model:model:list")
    @GetMapping("/list")
    public TableDataInfo<MdModelVo> list(MdModelBo bo, PageQuery pageQuery) {
        return mdModelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出模型列表
     */
    @SaCheckPermission("model:model:export")
    @Log(title = "模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MdModelBo bo, HttpServletResponse response) {
        List<MdModelVo> list = mdModelService.queryList(bo);
        ExcelUtil.exportExcel(list, "模型", MdModelVo.class, response);
    }

    /**
     * 获取模型详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("model:model:query")
    @GetMapping("/{id}")
    public R<MdModelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(mdModelService.queryById(id));
    }

    /**
     * 新增模型
     */
    @SaCheckPermission("model:model:add")
    @Log(title = "模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MdModelBo bo) {
        return toAjax(mdModelService.insertByBo(bo));
    }

    /**
     * 修改模型
     */
    @SaCheckPermission("model:model:edit")
    @Log(title = "模型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MdModelBo bo) {
        return toAjax(mdModelService.updateByBo(bo));
    }

    /**
     * 删除模型
     *
     * @param ids 主键串
     */
    @SaCheckPermission("model:model:remove")
    @Log(title = "模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(mdModelService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("model:model:edit")
    @Log(title = "模型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody MdModelBo model) {
        return toAjax(mdModelService.updateStatus(model.getId(), model.getStatus()));
    }
}
