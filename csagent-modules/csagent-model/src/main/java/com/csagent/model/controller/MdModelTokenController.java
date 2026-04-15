package com.csagent.model.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.csagent.common.core.domain.R;
import com.csagent.common.core.validate.AddGroup;
import com.csagent.common.core.validate.EditGroup;
import com.csagent.common.excel.utils.ExcelUtil;
import com.csagent.common.idempotent.annotation.RepeatSubmit;
import com.csagent.common.log.annotation.Log;
import com.csagent.common.log.enums.BusinessType;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.web.core.BaseController;
import com.csagent.model.domain.bo.MdModelTokenBo;
import com.csagent.model.domain.vo.MdModelTokenVo;
import com.csagent.model.service.IMdModelTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型token
 *
 * @author haipeng-lin
 * @date 2026-04-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/model/modelToken")
public class MdModelTokenController extends BaseController {

    private final IMdModelTokenService mdModelTokenService;

    /**
     * 查询模型token列表
     */
    @SaCheckPermission("model:modelToken:list")
    @GetMapping("/list")
    public TableDataInfo<MdModelTokenVo> list(MdModelTokenBo bo, PageQuery pageQuery) {
        return mdModelTokenService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出模型token列表
     */
    @SaCheckPermission("model:modelToken:export")
    @Log(title = "模型token", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MdModelTokenBo bo, HttpServletResponse response) {
        List<MdModelTokenVo> list = mdModelTokenService.queryList(bo);
        ExcelUtil.exportExcel(list, "模型token", MdModelTokenVo.class, response);
    }

    /**
     * 获取模型token详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("model:modelToken:query")
    @GetMapping("/{id}")
    public R<MdModelTokenVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(mdModelTokenService.queryById(id));
    }

    /**
     * 新增模型token
     */
    @SaCheckPermission("model:modelToken:add")
    @Log(title = "模型token", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MdModelTokenBo bo) {
        return toAjax(mdModelTokenService.insertByBo(bo));
    }

    /**
     * 修改模型token
     */
    @SaCheckPermission("model:modelToken:edit")
    @Log(title = "模型token", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MdModelTokenBo bo) {
        return toAjax(mdModelTokenService.updateByBo(bo));
    }

    /**
     * 删除模型token
     *
     * @param ids 主键串
     */
    @SaCheckPermission("model:modelToken:remove")
    @Log(title = "模型token", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(mdModelTokenService.deleteWithValidByIds(List.of(ids), true));
    }
}
