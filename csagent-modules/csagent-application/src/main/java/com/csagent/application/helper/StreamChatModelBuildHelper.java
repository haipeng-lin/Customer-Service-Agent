package com.csagent.application.helper;


import com.csagent.application.domain.AppApplication;
import com.csagent.model.domain.MdModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StreamChatModelBuildHelper {

    private MdModel model;

    private AppApplication application;

    @Autowired
    ApplicationHelper applicationHelper;

    public StreamingChatModel build(MdModel model, AppApplication application) {

        this.model = model;
        this.application = application;
        return switch (model.getFlag()) {
            // 本地
            case "ollama" -> buildOllama();
            // 通义千问
            case "qwen" -> buildQwen();
            // 通用
            default -> buildOpenAI();
        };

    }

    /**
     * 构建ollama
     *
     * @return StreamingChatModel 流式对话模型
     */
    private StreamingChatModel buildOllama() {
        return OllamaStreamingChatModel.builder()
                .baseUrl(model.getAddress())
                .modelName(application.getModelName())
                .build();
    }

    /**
     * 构建千问
     *
     * @return StreamingChatModel 流式对话模型
     */
    private StreamingChatModel buildQwen() {
        return QwenStreamingChatModel.builder()
                .apiKey(model.getKey())
                .modelName(application.getModelName())
                .temperature(application.getTemperature().floatValue())
                .build();
    }

    /**
     * 通过标准openai结构构建对象
     *
     * @return StreamingChatModel
     */
    private StreamingChatModel buildOpenAI() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(model.getAddress())
                .apiKey(model.getKey())
                .returnThinking(true)
                .modelName(application.getModelName())
                .temperature(application.getTemperature())
//                .topP(Double.valueOf(application.getTopRank()))
                .maxCompletionTokens(application.getMaxReplyToken())
                .listeners(List.of(applicationHelper.chatModelObservability()))
                .build();
    }
}
