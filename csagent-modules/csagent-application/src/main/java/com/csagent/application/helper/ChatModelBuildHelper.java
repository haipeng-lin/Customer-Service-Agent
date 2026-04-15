package com.csagent.application.helper;


import com.csagent.application.domain.AppApplication;
import com.csagent.model.domain.MdModel;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ChatModelBuildHelper {

    private MdModel model;

    private AppApplication application;

    @Autowired
    private ApplicationHelper applicationHelper;

    /**
     * 构建model
     *
     * @param model       model 模型
     * @param application ApplicationEntity
     * @return ChatModel
     */
    public ChatModel build(MdModel model, AppApplication application) {
        log.info("进入：{}", model);


        this.model = model;
        this.application = application;

        // Ollama
        if (model.getFlag().equals("ollama")) {
            return buildOllama();
        }

        // 通义千问
        if (model.getFlag().equals("qwen")) {
            return buildQwen();
        }

        // 千帆、豆包、GPT
        return buildOpenAI();
    }

    /**
     * 构建ollama
     *
     * @return ChatModel
     */
    private ChatModel buildOllama() {

        return OllamaChatModel.builder()
                .baseUrl(model.getAddress())
                .modelName(application.getModelName())
                .build();
    }

    /**
     * 构建千问
     *
     * @return ChatModel
     */
    private ChatModel buildQwen() {

        return QwenChatModel.builder()
                .apiKey(model.getKey())
                .modelName(application.getModelName())
                .temperature(application.getTemperature().floatValue())
                .listeners(List.of(applicationHelper.chatModelObservability()))
                .build();
    }

    /**
     * 通过标准openai结构构建对象
     *
     * @return ChatModel
     */
    private ChatModel buildOpenAI() {
        log.info("模型信息：{}", model.getKey());

        return OpenAiChatModel.builder()
                .baseUrl(model.getAddress())
                .apiKey(model.getKey())
                .modelName(application.getModelName())
                .listeners(List.of(applicationHelper.chatModelObservability()))
                .build();
    }
}
