//package com.csagent.web.controller.front;
//
//
//import com.csagent.application.domain.AppApplicationChat;
//import com.csagent.application.domain.AppChatSession;
//import com.csagent.application.service.IKbDatasetSearchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//
///**
// * @author haipeng-lin
// * @date 2026/3/28 9:15
// * @description: 对话Controller
// */
//@RequestMapping("/api/v1/chat")
//@RestController
//public class ChatController {
//
//    @Autowired
//    private IChatService chatService;
//
//    @Autowired
//    private IKbDatasetSearchService kbDatasetSearchService;
//
//    /**
//     * 开启会话
//     */
//    @PostMapping("/startSession")
//    public AjaxResult startSession(@RequestBody AppChatSession appChatSession) {
//        return success(chatService.startSession(appChatSession));
//    }
//
//    /**
//     * 获取对话日志
//     */
//    @GetMapping("/listChatLog")
//    public AjaxResult listChatLog(@RequestParam("sessionId") Long sessionId) {
//        return success(chatService.listChatLog(sessionId));
//    }
//
//    /**
//     * 应用聊天
//     */
//    @PostMapping(value = "/sseChat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter sseChat(@RequestBody AppApplicationChat appApplicationChat) {
//        return chatService.sseChat(appApplicationChat);
//    }
//
//    /**
//     * 评价对话
//     */
//    @PostMapping("/appraise")
//    public AjaxResult appraise(@RequestBody AppAppraise appAppraise) {
//        chatService.appraise(appAppraise);
//        return success();
//    }
//
//}
