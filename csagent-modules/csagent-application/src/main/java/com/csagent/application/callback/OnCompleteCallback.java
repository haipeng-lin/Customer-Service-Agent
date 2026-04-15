package com.csagent.application.callback;

import dev.langchain4j.model.chat.response.ChatResponse;

import java.util.List;
import java.util.Map;

/**
 * @author haipeng-lin
 * @date 2026/3/26 10:29
 * @description: 回调接口
 */
@FunctionalInterface
public interface OnCompleteCallback {

    void onComplete(ChatResponse response, long duration, List<Map<String, Object>> sourceContent);

}
