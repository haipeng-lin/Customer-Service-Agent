package com.csagent.application.helper.memory;

import com.csagent.common.redis.utils.RedisUtils;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/27 17:06
 * @description: Redis记忆辅助类
 */
@Slf4j
@Service
public class RedisMemoryBuilderHelper implements ChatMemoryStore {

    private static final String CHAT_MEMORY_PREFIX = "ai:chat:memory:";

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String key = getCacheKey(memoryId);
        String json = RedisUtils.getCacheObject(key);
        if (json == null || json.isEmpty()) {
            log.info("会话：{},消息为空！", memoryId);
            return new ArrayList<>();
        }
        log.info("会话内容：{}", json);
        // 将 JSON 字符串反序列化为对象
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String key = getCacheKey(memoryId);
        // 将对象序列化为 JSON 字符串
        String json = ChatMessageSerializer.messagesToJson(messages);
        RedisUtils.setCacheObject(key, json);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        RedisUtils.deleteObject(getCacheKey(memoryId));
    }

    private String getCacheKey(Object memoryId) {
        return CHAT_MEMORY_PREFIX + memoryId.toString();
    }
}
