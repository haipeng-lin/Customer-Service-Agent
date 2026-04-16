package com.csagent.application.helper;

import com.csagent.knowledge.mapper.KbDatasetMapper;
import com.csagent.model.mapper.MdModelMapper;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/9 14:36
 * @description: 应用辅助
 */
@Slf4j
@Component
public class ApplicationHelper {

//    @Autowired
//    private AppApplicationDatasetRelationMapper appApplicationDatasetRelationMapper;

    @Autowired
    private KbDatasetMapper kbDatasetMapper;

    @Autowired
    private MdModelMapper mdModelMapper;

    @Autowired
//    private MdModelTokenMapper mdModelTokenMapper;

    /**
     * 构建 ChatModel 监听器
     *
     * @return ChatModelListener
     */
    public ChatModelListener chatModelObservability() {

        return new ChatModelListener() {

            @Override
            public void onRequest(ChatModelRequestContext requestContext) {

                ChatRequest chatRequest = requestContext.chatRequest();
                List<ChatMessage> messages = chatRequest.messages();
                log.info("=== LangChain4j 可观测性 - 请求开始 ===");
                log.info("调用的消息数量: {}", messages.size());

                // 详细记录每条消息
                for (int i = 0; i < messages.size(); i++) {
                    ChatMessage message = messages.get(i);
                    log.info("消息 {}: 类型={}", i + 1, message.type());

                    // 根据消息类型获取内容
                    switch (message.type()) {
                        case USER:
                            log.info("用户消息内容: {}", ((dev.langchain4j.data.message.UserMessage) message).singleText());
                            break;
                        case AI:
                            log.info("AI消息内容: {}", ((dev.langchain4j.data.message.AiMessage) message).text());
                            break;
                        case SYSTEM:
                            log.info("系统消息内容: {}", ((dev.langchain4j.data.message.SystemMessage) message).text());
                            break;
                        case TOOL_EXECUTION_RESULT:
                            log.info("工具执行结果: {}", ((dev.langchain4j.data.message.ToolExecutionResultMessage) message).text());
                            break;
                        default:
                            log.info("消息内容: {}", message.toString());
                            break;
                    }
                }

                ChatRequestParameters parameters = chatRequest.parameters();
                log.info("调用的参数: modelName={}, temperature={}, topP={}",
                    parameters.modelName(),
                    parameters.temperature(),
                    parameters.topP());

                log.info("完整请求参数: {}", parameters);
                log.info("=== LangChain4j 可观测性 - 请求结束 ===");
            }

            @Override
            public void onResponse(ChatModelResponseContext responseContext) {
                ChatResponse chatResponse = responseContext.chatResponse();
                log.info("=== LangChain4j 可观测性 - 响应开始 ===");
                log.info("响应内容: {}", chatResponse.aiMessage());

                if (chatResponse.metadata() != null && chatResponse.metadata().tokenUsage() != null) {
                    log.info("Token使用情况: 输入Token={}, 输出Token={}, 总Token={}",
                        chatResponse.metadata().tokenUsage().inputTokenCount(),
                        chatResponse.metadata().tokenUsage().outputTokenCount(),
                        chatResponse.metadata().tokenUsage().totalTokenCount());
                }

                if (chatResponse.metadata() != null && chatResponse.metadata().finishReason() != null) {
                    log.info("完成原因: {}", chatResponse.metadata().finishReason());
                }

                log.info("=== LangChain4j 可观测性 - 响应结束 ===");
            }

            @Override
            public void onError(ChatModelErrorContext errorContext) {
                Throwable throwable = errorContext.error();
                log.error("=== LangChain4j 可观测性 - 错误开始 ===");
                log.error("调用AI模型时发生错误: {}", throwable.getMessage(), throwable);

                // 记录错误信息
                log.error("AI模型调用错误详情: {}", throwable.getMessage());

                // 记录请求信息
                if (errorContext.chatRequest() != null) {
                    log.error("出错时的请求信息: {}", errorContext.chatRequest());
                }

                // Also log the stack trace for debugging
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                log.error("完整的错误堆栈信息: {}", sw.toString());
                log.error("=== LangChain4j 可观测性 - 错误结束 ===");
            }
        };
    }

    /**
     * 获取关联知识库列表
     *
     * @param applicationId 知识库Id
     * @return 结果
     */
//    public List<KbDatasetSimple> getRelationDatasetList(Long applicationId) {
//        List<KbDatasetSimple> kbDatasetSimpleList = new LinkedList<>();
//
//        List<AppApplicationDatasetRelation> relationEntityList = appApplicationDatasetRelationMapper.selectByApplicationId(applicationId);
//        if (!CollectionUtils.isEmpty(relationEntityList)) {
//
//            List<Long> datasetIds = relationEntityList.stream().map(AppApplicationDatasetRelation::getDatasetId).toList();
//            List<KbDataset> datasetList = kbDatasetMapper.selectByIds(datasetIds);
//            for (KbDataset kbDataset : datasetList) {
//                KbDatasetSimple dto = new KbDatasetSimple();
//                BeanUtils.copyProperties(kbDataset, dto);
//                kbDatasetSimpleList.add(dto);
//            }
//        }
//
//        return kbDatasetSimpleList;
//    }

//    /**
//     * 记录token使用日志
//     *
//     * @param kbDataset 知识库
//     * @param response  Response<Embedding>
//     */
//    public void writeEmbeddingTokenLog(KbDataset kbDataset, Response<Embedding> response) {
//        TokenUsage tokenUsage = response.tokenUsage();
//        // Ollama的一些本地模型，没有返回使用的token
//        if (tokenUsage != null) {
//            MdModel mdModel = mdModelMapper.selectMdModelById(kbDataset.getModelId());
//            MdModelToken mdModelToken = new MdModelToken();
//            mdModelToken.setSource(TokenConsumeSource.EMBEDDING.getCode());
//            mdModelToken.setModelId(mdModel.getId());
//            mdModelToken.setInputToken(tokenUsage.inputTokenCount());
//            mdModelToken.setOutputToken(tokenUsage.outputTokenCount());
//            mdModelToken.setTotalToken(tokenUsage.totalTokenCount());
//            mdModelToken.setCreateTime(DateUtils.getNowDate());
//            mdModelTokenMapper.insertMdModelToken(mdModelToken);
//        }
//    }
//
//    /**
//     * 记录token日志
//     *
//     * @param modelId    模型Id
//     * @param source     来源
//     * @param tokenUsage token使用
//     */
//    public void writeTokenLog(Long modelId, String source, TokenUsage tokenUsage) {
//        MdModelToken mdModelToken = new MdModelToken();
//        mdModelToken.setModelId(modelId);
//        mdModelToken.setSource(source);
//        mdModelToken.setInputToken(tokenUsage.inputTokenCount());
//        mdModelToken.setOutputToken(tokenUsage.outputTokenCount());
//        mdModelToken.setTotalToken(tokenUsage.totalTokenCount());
//        mdModelToken.setCreateTime(DateUtils.getNowDate());
//        mdModelTokenMapper.insertMdModelToken(mdModelToken);
//    }
}
