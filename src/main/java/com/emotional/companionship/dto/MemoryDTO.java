package com.emotional.companionship.dto;

import lombok.Data;

/**
 * 记忆数据传输对象
 */
@Data
public class MemoryDTO {
    /**
     * 记忆唯一标识
     */
    private String id;
    
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
    private String date;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 关联的数字人信息
     */
    private DigitalHumanSimpleDTO digitalHuman;
    
    /**
     * 简化版数字人DTO，用于记忆详情
     */
    @Data
    public static class DigitalHumanSimpleDTO {
        private String id;
        private String name;
        private String relation;
    }
} 