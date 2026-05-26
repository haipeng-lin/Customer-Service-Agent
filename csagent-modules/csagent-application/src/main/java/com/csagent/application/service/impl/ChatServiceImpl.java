package com.csagent.application.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.csagent.application.domain.*;
import com.csagent.application.helper.ApplicationHelper;
import com.csagent.application.helper.SseEmitterHelper;
import com.csagent.application.mapper.AppApplicationMapper;
import com.csagent.application.mapper.AppChatMessageMapper;
import com.csagent.application.mapper.AppChatSessionMapper;
import com.csagent.application.service.IChatService;
import com.csagent.common.core.constant.SseConstants;
import com.csagent.common.core.enums.TokenConsumeSource;
import com.csagent.common.core.enums.UniversalStatus;
import com.csagent.common.core.exception.ServiceException;
import com.csagent.common.core.utils.DateUtils;
import dev.langchain4j.exception.ModelNotFoundException;
import dev.langchain4j.service.TokenStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

/**
 * @author haipeng-lin
 * @date 2026/3/28 9:36
 * @description: 对话Service实现
 */
@Slf4j
@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private AppChatSessionMapper appChatSessionMapper;

    @Autowired
    private AppChatMessageMapper appChatMessageMapper;

    @Autowired
    private ApplicationHelper applicationHelper;

    @Autowired
    private SseEmitterHelper sseEmitterHelper;

    @Autowired
    private AgentChat agentChat;

    @Autowired
    private AppApplicationMapper appApplicationMapper;

    @Override
    public Long startSession(AppChatSession appChatSession) {
        appChatSession.setUserId(SecurityUtils.getUserId());
        appChatSession.setTitle(DateUtils.getDateFormat() + "-" + SecurityUtils.getUsername());
        appChatSession.setSource(ChatSourceType.WEB.getCode());
        appChatSession.setStatus(UniversalStatus.ENABLE.getCode());
        int row = appChatSessionMapper.insertAppChatSession(appChatSession);
        if (row > 0) {
            return appChatSession.getId();
        }
        return 0L;
    }

    @Override
    public List<AppChatMessage> listChatLog(Long sessionId) {
        return appChatMessageMapper.selectListBySessionId(sessionId);
    }

    @Override
    public SseEmitter sseChat(AppApplicationChat applicationChat) {

        // 保存用户消息
        AppChatMessage userMessage = new AppChatMessage();
        userMessage.setUserId(SecurityUtils.getUserId());
        userMessage.setApplicationId(applicationChat.getApplicationId());
        userMessage.setSessionId(applicationChat.getSessionId());
        userMessage.setRole(ChatRoleType.USER.getCode());
        userMessage.setContent(applicationChat.getContent());
        appChatMessageMapper.insertAppChatMessage(userMessage);

        Long userId = SecurityUtils.getUserId();
        SseEmitter emitter = new SseEmitter(300000L);
        try {
            // 获取应用信息
            AppApplication application = appApplicationMapper.selectAppApplicationById(applicationChat.getApplicationId());
            if (application == null) {
                throw new ServiceException("应用配置错误");
            }
            try {
                TokenStream tokenStream = agentChat.streamChat(application, applicationChat);
                // 异步发送消息
                sseEmitterHelper.asyncSend2Client(tokenStream, emitter, 0, "", (response, time, sources) -> {
                    // 保存AI消息
                    AppChatMessage agentMessage = new AppChatMessage();
                    agentMessage.setUserId(userId); // 使用预先获取的 userId
                    agentMessage.setApplicationId(applicationChat.getApplicationId());
                    agentMessage.setSessionId(applicationChat.getSessionId());
                    agentMessage.setRole(ChatRoleType.AGENT.getCode());
                    agentMessage.setContent(response.aiMessage().text());
                    agentMessage.setFeedback("0");
                    if (CollUtil.isNotEmpty(sources)) {
                        agentMessage.setSourceContent(JSONUtil.toJsonStr(sources));
                    }
                    if (response.tokenUsage() != null) {
                        agentMessage.setToken(Long.valueOf(response.tokenUsage().totalTokenCount()));
                    }
                    agentMessage.setLatencyMs(time);
                    appChatMessageMapper.insertAppChatMessage(agentMessage);
                    // 2、消耗token
                    applicationHelper.writeTokenLog(application.getModelId(), TokenConsumeSource.CHAT.getCode(), response.tokenUsage());
                    // 返回消息ID
                    return agentMessage.getId();
                });
            } catch (Exception e) {
                log.error("构建AI服务时发生错误: {}", e.getMessage(), e);
                throw e; // Re-throw to be caught by the outer catch block
            }
        } catch (ModelNotFoundException e) {
            log.error("模型未找到异常：", e);
            try {
                // 发送友好的错误信息给前端
                String friendlyMessage = "您选择的AI模型暂时不可用，请联系管理员检查模型配置";
                emitter.send(SseEmitter.event().name(SseConstants.ERROR)
                        .data(friendlyMessage));
            } catch (IOException e2) {
                log.error("发送SSE错误信息失败", e2);
            } finally {
                emitter.complete();
            }
        } catch (Exception e) {
            log.error("构建ai服务出现了问题：", e);
            try {
                // 发送通用错误信息给前端
                String errorMessage = "系统繁忙，请稍后再试";
                emitter.send(SseEmitter.event().name(SseConstants.ERROR)
                        .data(errorMessage));
            } catch (IOException e2) {
                log.error("发送SSE错误信息失败", e2);
            } finally {
                emitter.complete();
            }
        }

        return emitter;
    }

    @Override
    public void appraise(AppAppraise appAppraise) {
        AppChatMessage appChatMessage = appChatMessageMapper.selectAppChatMessageById(appAppraise.getMessageId());
        if (appChatMessage == null) {
            throw new ServiceException("消息不能为空");
        }
        appChatMessage.setFeedback(String.valueOf(appAppraise.getAppraise()));
        appChatMessage.setUpdateTime(DateUtils.getNowDate());
        appChatMessageMapper.updateAppChatMessage(appChatMessage);
    }

}
