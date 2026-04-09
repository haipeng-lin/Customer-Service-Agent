package org.dromara.model.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.model.domain.vo.MdModelVo;
import org.dromara.model.domain.bo.MdModelBo;
import org.dromara.model.service.IMdModelService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

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
}
