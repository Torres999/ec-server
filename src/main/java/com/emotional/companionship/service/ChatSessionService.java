package com.emotional.companionship.service;

import com.emotional.companionship.dto.ChatSessionDTO;
import com.emotional.companionship.dto.request.EndChatRequest;
import com.emotional.companionship.dto.request.StartChatRequest;
import com.emotional.companionship.entity.ChatSession;

/**
 * 聊天会话服务接口
 */
public interface ChatSessionService {

    /**
     * 开始视频对话
     */
    ChatSessionDTO startChat(StartChatRequest request, String userId);

    /**
     * 结束视频对话
     */
    ChatSessionDTO endChat(EndChatRequest request, String userId);

    /**
     * 获取会话详情
     */
    ChatSession getSessionById(String id);

    /**
     * 检查会话是否属于用户
     */
    boolean isSessionBelongToUser(String id, String userId);

    /**
     * 检查用户是否有进行中的会话
     */
    boolean hasOngoingSession(String userId);

    /**
     * 获取用户的进行中会话
     */
    ChatSession getOngoingSession(String userId);

    /**
     * 获取用户的总聊天时长（分钟）
     */
    int getTotalChatMinutes(String userId);

    /**
     * 构建WebSocket URL
     */
    String buildWebSocketUrl(String sessionId);
} 