package com.emotional.companionship.dto;

import lombok.Data;
import java.util.Date;

/**
 * 聊天会话响应DTO
 */
@Data
public class ChatSessionResponseDTO {
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * WebSocket URL
     */
    private String webSocketUrl;
    
    /**
     * 结束时间（可选，结束会话时返回）
     */
    private String endTime;
    
    /**
     * 持续时间，单位：秒（可选，结束会话时返回）
     */
    private Integer duration;
} 