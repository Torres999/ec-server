package com.emotional.companionship.dto;

import lombok.Data;
import java.util.Date;

/**
 * 数字人数据传输对象
 */
@Data
public class DigitalHumanDTO {
    /**
     * 数字人唯一标识
     */
    private String id;
    
    /**
     * 数字人称呼
     */
    private String name;
    
    /**
     * 关系，可选值: "亲子"、"好友"、"其他"
     */
    private String relation;
    
    /**
     * 性格特征，可选值: "温柔善解人意"、"聪明伶牙俐齿"
     */
    private String personality;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
    
    /**
     * 上次对话时间
     */
    private String lastChatTime;
} 