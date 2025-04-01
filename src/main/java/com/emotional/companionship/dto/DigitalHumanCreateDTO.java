package com.emotional.companionship.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 创建数字人请求DTO
 */
@Data
public class DigitalHumanCreateDTO {
    /**
     * 数字人称呼
     */
    @NotBlank(message = "数字人称呼不能为空")
    private String name;
    
    /**
     * 关系
     */
    @NotBlank(message = "关系不能为空")
    @Pattern(regexp = "亲子|好友|其他", message = "关系可选值为：亲子、好友、其他")
    private String relation;
    
    /**
     * 性格特征
     */
    @NotBlank(message = "性格特征不能为空")
    @Pattern(regexp = "温柔善解人意|聪明伶牙俐齿", message = "性格特征可选值为：温柔善解人意、聪明伶牙俐齿")
    private String personality;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
} 