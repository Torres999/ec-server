package com.emotional.companionship.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class User {
    /**
     * 用户唯一标识
     */
    private String id;
    
    /**
     * 用户展示ID
     */
    private String userId;
    
    /**
     * 用户名称
     */
    private String name;
    
    /**
     * 用户描述
     */
    private String description;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 注册日期
     */
    private Date registerDate;
    
    /**
     * VIP等级
     */
    private Integer vipLevel;
    
    /**
     * VIP到期日期
     */
    private Date vipExpireDate;
    
    /**
     * 账户余额
     */
    private BigDecimal balance;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 