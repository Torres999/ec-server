package com.emotional.companionship.entity;

import lombok.Data;
import java.util.Date;

/**
 * 记忆实体类
 */
@Data
public class Memory {
    /**
     * 记忆唯一标识
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
     * 记忆标题
     */
    private String title;
    
    /**
     * 记忆内容
     */
    private String content;
    
    /**
     * 记忆日期
     */
    private Date memoryDate;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 