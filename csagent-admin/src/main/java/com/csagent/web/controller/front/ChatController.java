package com.csagent.web.controller.front;


import com.csagent.application.domain.AppApplicationChat;
import com.csagent.application.domain.AppAppraise;
import com.csagent.application.domain.AppChatMessage;
import com.csagent.application.domain.AppChatSession;
import com.csagent.application.service.IChatService;
import com.csagent.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


/**
 * @author haipeng-lin
 * @date 2026/3/28 9:15
 * @description: 对话Controller
 */
@RequestMapping("/api/v1/chat")
@RestController
public class ChatController {

    @Autowired
    private IChatService chatService;

    /**
     * 开启会话
     */
    @PostMapping("/startSession")
    public R<Long> startSession(@RequestBody AppChatSession appChatSession) {
        return R.ok(chatService.startSession(appChatSession));
    }

    /**
     * 获取对话日志
     */
    @GetMapping("/listChatLog")
    public R<List<AppChatMessage>> listChatLog(@RequestParam("sessionId") Long sessionId) {
        return R.ok(chatService.listChatLog(sessionId));
    }

    /**
     * 应用聊天
     */
    @PostMapping(value = "/sseChat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseChat(@RequestBody AppApplicationChat appApplicationChat) {
        return chatService.sseChat(appApplicationChat);
    }

    /**
     * 评价对话
     */
    @PostMapping("/appraise")
    public R<Void> appraise(@RequestBody AppAppraise appAppraise) {
        chatService.appraise(appAppraise);
        return R.ok();
    }

}
