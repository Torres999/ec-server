package com.emotional.companionship.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 开始视频对话请求
 */
@Data
public class StartChatRequest {
    /**
     * 数字人ID
     */
    @NotBlank(message = "数字人ID不能为空")
    private String digitalHumanId;
} 