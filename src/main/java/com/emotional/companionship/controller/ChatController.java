package com.emotional.companionship.controller;

import com.emotional.companionship.common.ApiResponse;
import com.emotional.companionship.dto.ChatSessionDTO;
import com.emotional.companionship.dto.request.EndChatRequest;
import com.emotional.companionship.dto.request.StartChatRequest;
import com.emotional.companionship.service.ChatSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 聊天控制器
 */
@Slf4j
@RestController
@RequestMapping("/chat")
@Api(tags = "视频对话管理")
public class ChatController {

    @Autowired
    private ChatSessionService chatSessionService;

    @PostMapping("/start")
    @ApiOperation("开始视频对话")
    public ApiResponse<ChatSessionDTO> startChat(
            @ApiParam(value = "开始视频对话请求", required = true) @Valid @RequestBody StartChatRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        ChatSessionDTO response = chatSessionService.startChat(request, userId);
        return ApiResponse.success("会话创建成功", response);
    }

    @PostMapping("/end")
    @ApiOperation("结束视频对话")
    public ApiResponse<ChatSessionDTO> endChat(
            @ApiParam(value = "结束视频对话请求", required = true) @Valid @RequestBody EndChatRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        ChatSessionDTO response = chatSessionService.endChat(request, userId);
        return ApiResponse.success("会话已结束", response);
    }
} 