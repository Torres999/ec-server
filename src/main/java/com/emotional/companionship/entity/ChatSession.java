package com.emotional.companionship.entity;

import lombok.Data;
import java.util.Date;

/**
 * 聊天会话实体类
 */
@Data
public class ChatSession {
    /**
     * 会话唯一标识
     */
    private String id;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 数字人ID
     */
    private String digitalHumanId;
    
    /**
     * 开始时间
     */
    private Date startTime;
    
    /**
     * 结束时间
     */
    private Date endTime;
    
    /**
     * 持续时间(秒)
     */
    private Integer duration;
    
    /**
     * 状态: "ongoing", "ended"
     */
    private String status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 