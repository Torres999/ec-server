package com.emotional.companionship.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录实体类
 */
@Data
public class PaymentRecord {
    /**
     * 记录唯一标识
     */
    private String id;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 套餐名称
     */
    private String packageName;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 支付方式
     */
    private String paymentMethod;
    
    /**
     * 支付状态
     */
    private String status;
    
    /**
     * 支付时间
     */
    private Date paymentTime;
    
    /**
     * 有效期开始
     */
    private Date validityStart;
    
    /**
     * 有效期结束
     */
    private Date validityEnd;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 