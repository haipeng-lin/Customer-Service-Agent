package com.csagent.application.helper;


import com.csagent.application.domain.AppApplication;
import com.csagent.application.domain.AppApplicationChat;
import com.csagent.application.helper.memory.MemoryMemoryBuilderHelper;
import com.csagent.application.helper.memory.RedisMemoryBuilderHelper;
import com.csagent.application.retriever.CsAgentEmbeddingStoreContentRetriever;
import com.csagent.application.service.IKbDatasetSearchService;
import com.csagent.common.langchain4j.service.IAiService;
import com.csagent.common.satoken.utils.LoginHelper;
import com.csagent.knowledge.domain.KbDataset;
import com.csagent.knowledge.domain.KbDatasetSearch;
import com.csagent.knowledge.mapper.KbDatasetMapper;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.aggregator.ContentAggregator;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.transformer.QueryTransformer;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author haipeng-lin
 * @date 2026/3/9 16:40
 * @description: Ai助手辅助
 */
@Component
@Slf4j
public class AssistantBuildHelper {

    @Autowired
    private IKbDatasetSearchService datasetSearchService;

    @Autowired
    private EmbeddingModelBuildHelper embeddingModelBuildHelper;

    @Autowired
    private StreamChatModelBuildHelper streamChatModelBuildHelper;

    @Autowired
    private KbDatasetMapper kbDatasetMapper;

    @Autowired
    private MemoryMemoryBuilderHelper memoryMemoryBuilderHelper;

    @Autowired
    private RedisMemoryBuilderHelper redisMemoryBuilderHelper;

    @Autowired
    private ApplicationHelper applicationHelper;

    /**
     * 构建 assistant
     *
     * @param applicationChat ApplicationSaveValidate
     * @param streamingModel  StreamingChatModel
     * @param chatModel       ChatModel
     * @return IAiService
     */
    public IAiService build(AppApplication application, AppApplicationChat applicationChat,
                            StreamingChatModel streamingModel, ChatModel chatModel) {

        // 自定义构建上下文记忆
        String memoryKey = applicationChat.getSessionId() + "_" + LoginHelper.getUserId();
        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
            .id(memoryKey)
            .maxMessages(application.getMemoryNum())
            .chatMemoryStore(redisMemoryBuilderHelper)
            .build();

        // 更新上下文记忆
//        if (applicationChat.getContextId() != 0) {
//            ApplicationWorkflowRuntimeContextEntity runtimeContextEntity
//                    = applicationWorkflowRuntimeContextMapper.selectById(applicationChat.getContextId());
//            JSONObject outputData = JSONUtil.parseObj(runtimeContextEntity.getOutputData());
//            outputData.set("log.context", messagesToJson(memoryBuildHelper.getMessages(memoryKey)));
//            runtimeContextEntity.setOutputData(outputData.toString());
//            applicationWorkflowRuntimeContextMapper.updateById(runtimeContextEntity);
//        }

        // 构建服务
        AiServices<IAiService> builder =
            AiServices.builder(IAiService.class)
                .streamingChatModel(streamingModel)
                .chatMemoryProvider(chatMemoryProvider);
//        .registerListener(applicationHelper.observability());
        // 未关联知识库
        if (applicationChat.getDatasetList() == null || applicationChat.getDatasetList().isEmpty()) {
            return builder.build();
        }

        // 关联了知识库
        QueryTransformer queryTransformer = null;

        // 构建交互数据
        KbDatasetSearch searchDataVo = new KbDatasetSearch();
        searchDataVo.setSearchType(application.getSearchMode());
        String datasetIds = applicationChat.getDatasetList().stream()
            .map(dto -> String.valueOf(dto.getId())) // 转为 String
            .collect(Collectors.joining(","));      // 用逗号连接

        searchDataVo.setDatasetIds(datasetIds);

        // 取第一条知识库的embedding模型当做全应用的embedding模型
        KbDataset kbDataset = kbDatasetMapper.selectById(applicationChat.getDatasetList().get(0).getId());

        // embedding模型
        EmbeddingModel embeddingModel = embeddingModelBuildHelper.build(kbDataset);

        // 内容检索
        ContentRetriever contentRetriever = CsAgentEmbeddingStoreContentRetriever.builder()
            .embeddingModel(embeddingModel)
            .searchService(datasetSearchService)
            .searchDataVo(searchDataVo)
            .maxResults(application.getTopRank()) // 召回数
            .minScore(application.getSimilarity().doubleValue()) // 相似度
            .build();
        // 检索增强
        RetrievalAugmentor retrievalAugmentor;
        ContentAggregator contentAggregator = null;

        DefaultRetrievalAugmentor.DefaultRetrievalAugmentorBuilder tempBuilder = DefaultRetrievalAugmentor.builder()
            .contentRetriever(contentRetriever); // 内容检索

        // 内容聚合
        retrievalAugmentor = tempBuilder.build();

        return builder
            .retrievalAugmentor(retrievalAugmentor) // 索引增强
            .build();
    }

}
