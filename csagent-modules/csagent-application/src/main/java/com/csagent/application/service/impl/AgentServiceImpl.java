package com.csagent.application.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csagent.application.mapper.AppApplicationMapper;
import com.csagent.application.mapper.AppChatMessageMapper;
import com.csagent.application.service.IAgentService;
import com.csagent.application.task.EmbeddingDocumentTask;
import com.csagent.application.task.EmbeddingQuestionTask;
import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.common.core.domain.vo.KbDocumentItemVo;
import com.csagent.common.core.enums.*;
import com.csagent.common.core.exception.ServiceException;
import com.csagent.common.core.utils.DateUtils;
import com.csagent.common.langchain4j.handler.file.FileHandleFactory;
import com.csagent.common.langchain4j.handler.file.FileHandlerInterface;
import com.csagent.common.satoken.utils.LoginHelper;
import com.csagent.knowledge.domain.*;
import com.csagent.knowledge.domain.vo.KbDocumentSplitVo;
import com.csagent.knowledge.mapper.*;
import com.csagent.model.domain.MdModel;
import com.csagent.model.mapper.MdModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/16 11:09
 * @description: Agent服务实现
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AgentServiceImpl implements IAgentService {


    @Autowired
    private KbDocumentMapper kbDocumentMapper;

    @Autowired
    private KbDatasetMapper kbDatasetMapper;

    @Autowired
    private KbParagraphMapper kbParagraphMapper;

    @Autowired
    private MdModelMapper mdModelMapper;

    @Autowired
    private AppApplicationMapper appApplicationMapper;


    @Autowired
    private AppChatMessageMapper appChatMessageMapper;

    @Autowired
    private EmbeddingDocumentTask embeddingDocumentTask;

    @Autowired
    private KbQuestionParagraphMapper kbQuestionParagraphMapper;

    @Autowired
    private EmbeddingQuestionTask embeddingQuestionTask;
    @Autowired
    private KbEmbeddingMapper kbEmbeddingMapper;


    @Override
    public void embeddingDocument(String documentIds) {
        List<Long> documentIdList = Arrays.stream(documentIds.split(","))
            .map(Long::valueOf)
            .toList();
        if (CollectionUtils.isEmpty(documentIdList)) {
            return;
        }
        for (Long documentId : documentIdList) {
            KbDocument document = kbDocumentMapper.selectById(documentId);
            if (document == null) {
                continue;
            }
            Long datasetId = document.getDatasetId();
            KbDataset kbDataset = kbDatasetMapper.selectById(datasetId);
            if (kbDataset == null) {
                continue;
            }
            // 标记开始向量化
            KbDocument updateDocument = kbDocumentMapper.selectById(documentId);
            updateDocument.setEmbeddingStatus(EmbeddingStatus.RUNNING.getCode());
            updateDocument.setUpdateTime(DateUtils.getNowDate());
            kbDocumentMapper.updateById(updateDocument);

            // 执行向量化
            embeddingDocumentTask.executeAsyncTask(documentId, kbDataset);
        }
    }

    @Override
    public void doQuestionRelation(KbQuestionRelationOperate questionRelation) {
        List<Long> questionIdList = Arrays.stream(questionRelation.getQuestionIds().split(",")).map(Long::valueOf).toList();

        KbDataset kbDataset = kbDatasetMapper.selectById(questionRelation.getDatasetId());

        for (Long questionId : questionIdList) {
            if (questionRelation.getType().equals(1)) {
                // 新增段落关联
                KbQuestionParagraph kbQuestionParagraph = new KbQuestionParagraph();
                kbQuestionParagraph.setDatasetId(questionRelation.getDatasetId());
                kbQuestionParagraph.setDocumentId(questionRelation.getDocumentId());
                kbQuestionParagraph.setParagraphId(questionRelation.getParagraphId());
                kbQuestionParagraph.setQuestionId(questionId);
                kbQuestionParagraph.setCreateBy(LoginHelper.getUserId());
                kbQuestionParagraph.setCreateTime(DateUtils.getNowDate());

                kbQuestionParagraphMapper.insert(kbQuestionParagraph);

                // 向量化问题
                embeddingQuestionTask.executeAsyncTask(questionId, questionRelation, kbDataset);
            } else {
                // 根据 问题Id、段落Id 删除 问题段落关联
                LambdaQueryWrapper<KbQuestionParagraph> lqw = new LambdaQueryWrapper<KbQuestionParagraph>()
                    .eq(KbQuestionParagraph::getQuestionId, questionId)
                    .eq(KbQuestionParagraph::getParagraphId, questionRelation.getParagraphId());
                kbQuestionParagraphMapper.delete(lqw);

                // 根据类型、来源Id、段落Id删除
                kbEmbeddingMapper.deleteByTypeAndSourceIdAndParagraphId(EmbeddingSourceType.QUESTION.getCode(), questionId, questionRelation.getParagraphId());
            }
        }
    }

    @Override
    public void embeddingDataset(Long datasetId) {
        KbDataset kbDataset = kbDatasetMapper.selectById(datasetId);
        LambdaQueryWrapper<KbDocument> lqw = new LambdaQueryWrapper<KbDocument>().eq(KbDocument::getDatasetId, datasetId);
        List<KbDocument> documentList = kbDocumentMapper.selectList(lqw);
        for (KbDocument document : documentList) {

            // 标记开始向量化
            KbDocument updateDocument = kbDocumentMapper.selectById(document.getId());
            updateDocument.setEmbeddingStatus(EmbeddingStatus.RUNNING.getCode());
            updateDocument.setUpdateTime(DateUtils.getNowDate());
            kbDocumentMapper.updateById(updateDocument);

            // 执行向量化
            embeddingDocumentTask.executeAsyncTask(document.getId(), kbDataset);
        }
    }

    @Override
    public void generateQuestion(KbQuestionGeneration questionGeneration) {
        if (questionGeneration.getPrompt().isBlank()) {
            throw new ServiceException("提示词不能为空");
        }

        // 要生成问题的文档
        List<Long> documentIds = Arrays.stream(questionGeneration.getDocumentIds().split(","))
            .map(Long::valueOf)
            .toList();
        if (CollectionUtils.isEmpty(documentIds)) {
            throw new ServiceException("文档不能为空");
        }

        // 构建模型
        MdModel mdModel = mdModelMapper.selectById(questionGeneration.getModelId());
        if (mdModel == null) {
            throw new ServiceException("模型异常");
        }

        embeddingDocumentTask.executeAsyncQuestionTask(mdModel, documentIds, questionGeneration);
    }

    @Override
    public List<KbDocumentSplitVo> previewFile(KbDocumentPreview kbDocumentPreview) {
        try {

            List<KbDocumentSplitVo> splitList = new LinkedList<>();
            for (MultipartFile file : kbDocumentPreview.getFiles()) {

                KbDocumentSplitVo vo = new KbDocumentSplitVo();
                // 文本标题
                String originalFilename = file.getOriginalFilename();
                vo.setName(originalFilename);

                byte[] bytes = file.getBytes(); // 获取文件的字节数组

                // 文本大小
                vo.setFileSize(file.getSize());

                FileHandleFactory fileHandleFactory = new FileHandleFactory();

                // 选择文件处理器
                String ext = FileUtil.extName(originalFilename);
                if (ext == null) {
                    ext = "";
                }
                FileHandlerInterface fileHandle = fileHandleFactory.getSplitter(ext);
                List<KbDocumentItemVo> itemListVo = fileHandle.handle(bytes, kbDocumentPreview);

                vo.setContent(itemListVo);
                splitList.add(vo);
            }

            return splitList;

        } catch (IllegalStateException | IOException e) {
            throw new ServiceException("预览文档失败，" + e.getMessage());
        }
    }

    @Override
    public void saveDocument(KbDocumentSave kbDocumentSave) {
        for (KbDocumentSplitVo document : kbDocumentSave.getDocumentList()) {

            if (document.getContent().isEmpty()) {
                throw new ServiceException("文档内容为空");
            }

            // 写入文档
            KbDocument kbDocument = new KbDocument();
            kbDocument.setTitle(document.getName());
            kbDocument.setFileSize(document.getFileSize());
            kbDocument.setStatus(UniversalStatus.ENABLE.getCode());
            kbDocument.setQuestionStatus(QuestionStatus.PENDING.getCode());
            kbDocument.setDatasetId(kbDocumentSave.getDatasetId());
            kbDocument.setSegmentCount(document.getContent().size());
            kbDocument.setCreateBy(LoginHelper.getUserId());
            kbDocument.setCreateTime(DateUtils.getNowDate());

            kbDocumentMapper.insert(kbDocument);
            Long documentId = kbDocument.getId();

            // 写入段落
            for (KbDocumentItemVo content : document.getContent()) {

                KbParagraph paragraph = new KbParagraph();
                paragraph.setTitle(content.getTitle());
                paragraph.setContent(content.getContent());
                paragraph.setDatasetId(kbDocumentSave.getDatasetId());
                paragraph.setDocumentId(documentId);
                paragraph.setStatus(UniversalStatus.ENABLE.getCode());
                paragraph.setIndexStatus(ParagraphIndexStatus.PENDING.getCode());
                paragraph.setCreateBy(LoginHelper.getUserId());
                paragraph.setCreateTime(DateUtils.getNowDate());
                kbParagraphMapper.insert(paragraph);
            }
        }
    }

}
