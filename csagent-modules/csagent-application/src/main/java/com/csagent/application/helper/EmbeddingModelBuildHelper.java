package com.csagent.application.helper;


import com.csagent.knowledge.domain.KbDataset;
import com.csagent.model.domain.MdModel;
import com.csagent.model.mapper.MdModelMapper;
import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmbeddingModelBuildHelper {

    @Autowired
    private MdModelMapper modelMapper;

    private final Map<String, String> modelConfig = new HashMap<>();

    /**
     * 构建向量模型
     *
     * @param kbDataset 知识库
     * @return EmbeddingModel
     */
    public EmbeddingModel build(KbDataset kbDataset) {
        // 默认内存型的模型
//        if (dataset.getModelName().equals("AllMiniLmL6V2Embedding")) {
//            return new AllMiniLmL6V2EmbeddingModel();
//        }

        // 查询模型信息
        MdModel embeddingModel = modelMapper.selectById(kbDataset.getEmbeddingModelId());

        modelConfig.put("baseUrl", embeddingModel.getAddress());
        modelConfig.put("key", embeddingModel.getKey());
        modelConfig.put("model", kbDataset.getEmbeddingModelName());

        // ollama
        if (embeddingModel.getFlag().equals("ollama")) {
            return buildOllama();
        }

        // ollama
        if (embeddingModel.getFlag().equals("qwen")) {
            return buildQwen();
        }

        return buildOpenAI();
    }

    private EmbeddingModel buildQwen() {
        return QwenEmbeddingModel.builder()
                .apiKey(modelConfig.get("key"))
                .modelName(modelConfig.get("model"))
                .build();
    }

    /**
     * 构建智普
     *
     * @return EmbeddingModel
     */
    private EmbeddingModel buildOllama() {

        return OllamaEmbeddingModel.builder()
                .baseUrl(modelConfig.get("baseUrl"))
                .modelName(modelConfig.get("model"))
                .build();
    }

    /**
     *
     * @return EmbeddingModel
     */
    private EmbeddingModel buildOpenAI() {

        return OpenAiEmbeddingModel.builder()
                .baseUrl(modelConfig.get("baseUrl"))
                .apiKey(modelConfig.get("key"))
                .modelName(modelConfig.get("model"))
                .build();
    }
}
