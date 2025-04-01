package com.emotional.companionship.entity;

import lombok.Data;
import java.util.Date;

/**
 * 数字人实体类
 */
@Data
public class DigitalHuman {
    /**
     * 数字人唯一标识
     */
    private String id;
    
    /**
     * 所属用户ID
     */
    private String userId;
    
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
    private Date lastChatTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 