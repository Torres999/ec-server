package com.emotional.companionship.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 创建记忆请求
 */
@Data
public class CreateMemoryRequest {
    /**
     * 记忆标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    
    /**
     * 记忆内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    
    /**
     * 关联的数字人ID
     */
    @NotBlank(message = "数字人ID不能为空")
    private String digitalHumanId;
    
    /**
     * 图片URL
     */
    private String imageUrl;
} 