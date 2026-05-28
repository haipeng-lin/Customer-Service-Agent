package com.csagent.application.service;

import com.csagent.application.domain.AppApplication;
import com.csagent.application.domain.AppApplicationChat;
import com.csagent.common.core.domain.KbDocumentPreview;
import com.csagent.knowledge.domain.KbDocumentSave;
import com.csagent.knowledge.domain.KbQuestionGeneration;
import com.csagent.knowledge.domain.KbQuestionParagraph;
import com.csagent.knowledge.domain.KbQuestionRelationOperate;
import com.csagent.knowledge.domain.vo.KbDocumentSplitVo;
import dev.langchain4j.service.TokenStream;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/16 11:01
 * @description: Agent服务
 */
public interface IAgentService {
    /**
     * 生成问题
     *
     * @param questionGeneration 问题生成
     */
    void generateQuestion(KbQuestionGeneration questionGeneration);

    /**
     * 预览文档
     *
     * @param kbDocumentPreview 文档预览
     * @return 结果
     */
    List<KbDocumentSplitVo> previewFile(KbDocumentPreview kbDocumentPreview);

    /**
     * 保存文档
     *
     * @param kbDocumentSave 文档保存
     */
    void saveDocument(KbDocumentSave kbDocumentSave);


    /**
     * 向量化文档
     *
     * @param documentIds 文档Ids
     */
    void embeddingDocument(String documentIds);

    /**
     * 操作问题关联
     *
     * @param kbQuestionRelationOperate 问题关联
     */
    void doQuestionRelation(KbQuestionRelationOperate kbQuestionRelationOperate);

    /**
     * 向量化知识库
     *
     * @param datasetId 知识库Id
     */
    void embeddingDataset(Long datasetId);

    /**
     * 根据问题Id列表和知识库Id查询
     *
     * @param questionIdList 问题Id列表
     * @param datasetId      知识库Id
     * @return 结果
     */
    List<KbQuestionParagraph> selectByQuestionIdsAndDatasetId(List<Long> questionIdList, Long datasetId);

    /**
     * 流式对话
     *
     * @param application        应用
     * @param appApplicationChat 应用对话
     * @return 结果
     * @throws UnknownHostException
     */
    TokenStream streamChat(AppApplication application, AppApplicationChat appApplicationChat) throws UnknownHostException;

}
