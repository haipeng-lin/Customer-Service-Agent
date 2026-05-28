package com.csagent.application.service;


import com.csagent.application.domain.AppApplicationChat;
import com.csagent.application.domain.AppAppraise;
import com.csagent.application.domain.AppChatSession;
import com.csagent.application.domain.vo.AppChatMessageVo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/28 9:35
 * @description: 对话Service
 */
public interface IChatService {

    /**
     * 开启会话
     *
     * @param appChatSession 对话会话
     */
    Long startSession(AppChatSession appChatSession);

    /**
     * 查询对话日志
     *
     * @param sessionId 会话Id
     * @return 结果
     */
    List<AppChatMessageVo> listChatLog(Long sessionId);

    /**
     * sse对话
     *
     * @param applicationChat 应用对话
     * @return 结构
     */
    SseEmitter sseChat(AppApplicationChat applicationChat);

    /**
     * 评价
     *
     * @param appAppraise 评价
     */
    void appraise(AppAppraise appAppraise);
}
