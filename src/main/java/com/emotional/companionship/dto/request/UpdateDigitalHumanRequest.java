package com.emotional.companionship.dto.request;

import lombok.Data;
import javax.validation.constraints.Pattern;

/**
 * 更新数字人请求
 */
@Data
public class UpdateDigitalHumanRequest {
    /**
     * 数字人称呼
     */
    private String name;
    
    /**
     * 关系，可选值: "亲子"、"好友"、"其他"
     */
    @Pattern(regexp = "亲子|好友|其他", message = "关系只能是：亲子、好友、其他")
    private String relation;
    
    /**
     * 性格特征，可选值: "温柔善解人意"、"聪明伶牙俐齿"
     */
    @Pattern(regexp = "温柔善解人意|聪明伶牙俐齿", message = "性格特征只能是：温柔善解人意、聪明伶牙俐齿")
    private String personality;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
} 