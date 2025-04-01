package com.emotional.companionship.dto;

import lombok.Data;

/**
 * 聊天会话数据传输对象
 */
@Data
public class ChatSessionDTO {
    /**
     * 会话唯一标识
     */
    private String sessionId;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * 持续时间(秒)
     */
    private Integer duration;
    
    /**
     * WebSocket URL
     */
    private String webSocketUrl;
} 