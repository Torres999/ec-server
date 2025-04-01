package com.emotional.companionship.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户数据传输对象
 */
@Data
public class UserDTO {
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
} 