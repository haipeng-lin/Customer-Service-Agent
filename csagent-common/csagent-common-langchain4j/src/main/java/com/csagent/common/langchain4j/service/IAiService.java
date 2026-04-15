package com.csagent.common.langchain4j.service;

import dev.langchain4j.service.*;

import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/4/15 11:39
 * @description: Ai服务
 */
public interface IAiService {

    /**
     * 不带角色设定的流式输出
     *
     * @param userMessage String
     * @return TokenStream
     */
    TokenStream chatInTokenStream(String userMessage);

    /**
     * 带角色设定的流式输出
     *
     * @param systemMessage String
     * @param userMessage   String
     * @return TokenStream
     */
    @SystemMessage("{{message}}")
    TokenStream chatWithSystem(@V("message") String systemMessage, @UserMessage String userMessage);

    Result<List<String>> chat(String userMessage);
}
