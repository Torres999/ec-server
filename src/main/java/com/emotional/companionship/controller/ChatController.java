package com.emotional.companionship.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emotional.companionship.common.Result;
import com.emotional.companionship.dto.ChatSessionDTO;
import com.emotional.companionship.dto.request.EndChatRequest;
import com.emotional.companionship.dto.request.StartChatRequest;
import com.emotional.companionship.service.ChatSessionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * 聊天控制器
 */
@Slf4j
@RestController
@RequestMapping("/chat")
@Tag(name = "视频对话管理")
public class ChatController {

    @Autowired
    private ChatSessionService chatSessionService;

    @PostMapping("/start")
    @Operation(summary = "开始视频对话")
    public Result<ChatSessionDTO> startChat(
            @Parameter(description = "开始视频对话请求", required = true) @Valid @RequestBody StartChatRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        ChatSessionDTO response = chatSessionService.startChat(request, userId);
        return Result.success("会话创建成功", response);
    }

    @PostMapping("/end")
    @Operation(summary = "结束视频对话")
    public Result<ChatSessionDTO> endChat(
            @Parameter(description = "结束视频对话请求", required = true) @Valid @RequestBody EndChatRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        ChatSessionDTO response = chatSessionService.endChat(request, userId);
        return Result.success("会话已结束", response);
    }
} 