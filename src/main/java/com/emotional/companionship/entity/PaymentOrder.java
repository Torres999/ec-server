package com.emotional.companionship.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值订单实体类
 */
@Data
public class PaymentOrder {
    /**
     * 订单唯一标识
     */
    private String id;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 套餐ID
     */
    private String packageId;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 支付方式: "wechat", "alipay"
     */
    private String paymentMethod;
    
    /**
     * 订单状态: "created", "paid", "failed", "cancelled"
     */
    private String status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 支付时间
     */
    private Date paymentTime;
    
    /**
     * 订单过期时间
     */
    private Date expiryTime;
    
    /**
     * 第三方支付交易ID
     */
    private String transactionId;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 