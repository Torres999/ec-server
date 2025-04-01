package com.emotional.companionship.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 结束聊天会话请求DTO
 */
@Data
public class ChatSessionEndDTO {
    /**
     * 会话ID
     */
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;
} 