package com.emotional.companionship.service;

import com.emotional.companionship.entity.PaymentOrder;
import com.emotional.companionship.entity.PaymentRecord;

import java.util.Map;

/**
 * 支付服务接口
 */
public interface PaymentService {

    /**
     * 创建订单
     */
    Map<String, Object> createOrder(String packageId, String paymentMethod, String userId);

    /**
     * 支付回调处理
     */
    boolean handlePaymentCallback(String orderId, String transactionId, String status);

    /**
     * 获取订单
     */
    PaymentOrder getOrderById(String id);

    /**
     * 取消订单
     */
    boolean cancelOrder(String id, String userId);

    /**
     * 获取充值记录
     */
    Map<String, Object> getPaymentRecords(String userId, Integer page, Integer size);

    /**
     * 查询订单是否属于用户
     */
    boolean isOrderBelongToUser(String id, String userId);

    /**
     * 生成支付参数
     */
    Map<String, Object> generatePaymentParams(PaymentOrder order);
} 