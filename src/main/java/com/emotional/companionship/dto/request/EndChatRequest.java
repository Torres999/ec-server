package com.emotional.companionship.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 结束视频对话请求
 */
@Data
public class EndChatRequest {
    /**
     * 会话ID
     */
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;
} 