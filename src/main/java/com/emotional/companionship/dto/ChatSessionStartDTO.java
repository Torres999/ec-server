package com.emotional.companionship.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 开始聊天会话请求DTO
 */
@Data
public class ChatSessionStartDTO {
    /**
     * 数字人ID
     */
    @NotBlank(message = "数字人ID不能为空")
    private String digitalHumanId;
} 