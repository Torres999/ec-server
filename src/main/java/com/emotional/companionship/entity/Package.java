package com.emotional.companionship.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值套餐实体类
 */
@Data
public class Package {
    /**
     * 套餐唯一标识
     */
    private String id;
    
    /**
     * 套餐名称
     */
    private String name;
    
    /**
     * 套餐描述
     */
    private String description;
    
    /**
     * 当前价格
     */
    private BigDecimal price;
    
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    
    /**
     * 有效期(天)
     */
    private Integer duration;
    
    /**
     * 套餐权益列表，JSON格式
     */
    private String benefits;
    
    /**
     * 状态: "active", "inactive"
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