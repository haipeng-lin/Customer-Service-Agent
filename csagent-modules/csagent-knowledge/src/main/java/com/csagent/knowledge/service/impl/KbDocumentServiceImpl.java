package com.csagent.knowledge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csagent.common.core.utils.MapstructUtils;
import com.csagent.common.core.utils.StringUtils;
import com.csagent.common.mybatis.core.page.TableDataInfo;
import com.csagent.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csagent.knowledge.domain.KbDataset;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.csagent.knowledge.domain.bo.KbDocumentBo;
import com.csagent.knowledge.domain.vo.KbDocumentVo;
import com.csagent.knowledge.domain.KbDocument;
import com.csagent.knowledge.mapper.KbDocumentMapper;
import com.csagent.knowledge.service.IKbDocumentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文档Service业务层处理
 *
 * @author haipeng-lin
 * @date 2026-04-10
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KbDocumentServiceImpl implements IKbDocumentService {

    private final KbDocumentMapper baseMapper;

    /**
     * 查询文档
     *
     * @param id 主键
     * @return 文档
     */
    @Override
    public KbDocumentVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询文档列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 文档分页列表
     */
    @Override
    public TableDataInfo<KbDocumentVo> queryPageList(KbDocumentBo bo, PageQuery pageQuery) {
        // 1. 构建支持连表的 Wrapper
        MPJLambdaWrapper<KbDocument> wrapper = buildJoinWrapper(bo);

        // 2. 使用 selectJoinPage，第一个参数是 VO 的 Class，第二个是分页对象，第三个是 Wrapper
        IPage<KbDocumentVo> kbDocumentVoIPage = baseMapper.selectJoinPage(pageQuery.build(), KbDocumentVo.class, wrapper);

        return TableDataInfo.build(kbDocumentVoIPage);
    }

    /**
     * 查询符合条件的文档列表
     *
     * @param bo 查询条件
     * @return 文档列表
     */
    @Override
    public List<KbDocumentVo> queryList(KbDocumentBo bo) {
        LambdaQueryWrapper<KbDocument> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbDocument> buildQueryWrapper(KbDocumentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<KbDocument> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(KbDocument::getId);
        lqw.eq(bo.getDatasetId() != null, KbDocument::getDatasetId, bo.getDatasetId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), KbDocument::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), KbDocument::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), KbDocument::getContent, bo.getContent());

        lqw.eq(StringUtils.isNotBlank(bo.getEmbeddingStatus()), KbDocument::getEmbeddingStatus, bo.getEmbeddingStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getQuestionStatus()), KbDocument::getQuestionStatus, bo.getQuestionStatus());
        lqw.eq(bo.getQuestionTime() != null, KbDocument::getQuestionTime, bo.getQuestionTime());
        lqw.eq(StringUtils.isNotBlank(bo.getAnswerType()), KbDocument::getAnswerType, bo.getAnswerType());
        lqw.eq(bo.getRedirectSimilar() != null, KbDocument::getRedirectSimilar, bo.getRedirectSimilar());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbDocument::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增文档
     *
     * @param bo 文档
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(KbDocumentBo bo) {
        KbDocument add = MapstructUtils.convert(bo, KbDocument.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文档
     *
     * @param bo 文档
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(KbDocumentBo bo) {
        KbDocument update = MapstructUtils.convert(bo, KbDocument.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbDocument entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除文档信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public int updateStatus(Long id, String status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<KbDocument>()
                .set(KbDocument::getStatus, status)
                .eq(KbDocument::getId, id));
    }

    /**
     * 核心：构建连表 Wrapper
     */
    private MPJLambdaWrapper<KbDocument> buildJoinWrapper(KbDocumentBo bo) {
        MPJLambdaWrapper<KbDocument> wrapper = new MPJLambdaWrapper<KbDocument>()
            .selectAll(KbDocument.class) // 查询主表 KbDocument 的所有字段
            // 连表查询：查 KbDataset 表的 name 字段，映射到 KbDocumentVo 的 datasetName 属性
            .selectAs(KbDataset::getTitle, KbDocumentVo::getTitle)
            // 连表条件：KbDocument.datasetId = KbDataset.id
            .leftJoin(KbDataset.class, KbDataset::getId, KbDocument::getDatasetId);

        // --- 以下是原有的过滤条件 ---
        wrapper.orderByAsc(KbDocument::getId);
        wrapper.eq(bo.getDatasetId() != null, KbDocument::getDatasetId, bo.getDatasetId());
        wrapper.like(StringUtils.isNotBlank(bo.getTitle()), KbDocument::getTitle, bo.getTitle());
        wrapper.eq(StringUtils.isNotBlank(bo.getStatus()), KbDocument::getStatus, bo.getStatus());
        // ... 其他条件以此类推

        return wrapper;
    }
}
