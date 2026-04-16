package com.csagent.application.task;

import cn.hutool.core.util.IdUtil;
import com.csagent.application.helper.ApplicationHelper;
import com.csagent.application.helper.EmbeddingModelBuildHelper;
import com.csagent.common.core.enums.EmbeddingSourceType;
import com.csagent.common.core.enums.UniversalStatus;
import com.csagent.common.core.utils.DateUtils;
import com.csagent.common.core.utils.vector.TsVectorGeneratorUtils;
import com.csagent.knowledge.domain.KbDataset;
import com.csagent.knowledge.domain.KbEmbedding;
import com.csagent.knowledge.domain.KbQuestion;
import com.csagent.knowledge.domain.KbQuestionRelationOperate;
import com.csagent.knowledge.mapper.KbEmbeddingMapper;
import com.csagent.knowledge.mapper.KbQuestionMapper;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/16 14:12
 * @description: 向量问题任务
 */
@Component
public class EmbeddingQuestionTask {

    @Autowired
    private KbQuestionMapper kbQuestionMapper;

    @Autowired
    private KbEmbeddingMapper kbEmbeddingMapper;

    @Autowired
    private EmbeddingModelBuildHelper embeddingModelBuildHelper;

    @Autowired
    private ApplicationHelper applicationHelper;

    /**
     * 向量化 问题-段落
     *
     * @param questionId       问题id
     * @param questionRelation 问题关联
     * @param kbDataset        知识库
     */
    @Async
    public void executeAsyncTask(Long questionId, KbQuestionRelationOperate questionRelation, KbDataset kbDataset) {

        // 选择embedding模型
        EmbeddingModel embeddingModel = embeddingModelBuildHelper.build(kbDataset);

        KbQuestion kbQuestion = kbQuestionMapper.selectById(questionId);
        if (kbQuestion != null) {

            // 删除旧的关联
            kbEmbeddingMapper.deleteByTypeAndSourceIdAndParagraphId(EmbeddingSourceType.QUESTION.getCode(), questionId, questionRelation.getParagraphId());

            // 开始向量化，并入库
            KbEmbedding kbEmbedding = new KbEmbedding();
            kbEmbedding.setEmbeddingId(IdUtil.randomUUID());
            kbEmbedding.setDatasetId(kbQuestion.getDatasetId());
            kbEmbedding.setDocumentId(questionRelation.getDocumentId());
            kbEmbedding.setParagraphId(questionRelation.getParagraphId());

            Response<Embedding> response = embeddingModel.embed(kbQuestion.getContent());
            List<Float> vector = response.content().vectorAsList();
            kbEmbedding.setEmbedding(vector); // 向量化文本
            // 记录token消耗记录
            applicationHelper.writeEmbeddingTokenLog(kbDataset, response);

            kbEmbedding.setSearchVector(TsVectorGeneratorUtils.toTsVector(kbQuestion.getContent())); // 全文检索文本
            kbEmbedding.setStatus(UniversalStatus.ENABLE.getCode());
            kbEmbedding.setSourceType(EmbeddingSourceType.QUESTION.getCode()); // 来源问题
            kbEmbedding.setSourceId(questionId); // 来源id
            kbEmbedding.setCreateTime(DateUtils.getNowLocalDateTime());

            kbEmbeddingMapper.insert(kbEmbedding);
        }
    }
}
