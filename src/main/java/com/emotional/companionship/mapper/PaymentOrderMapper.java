package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.PaymentOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 充值订单Mapper接口
 */
@Mapper
public interface PaymentOrderMapper {

    /**
     * 根据ID查询订单
     */
    @Select("SELECT * FROM payment_order WHERE id = #{id}")
    PaymentOrder findById(@Param("id") String id);

    /**
     * 根据用户ID查询订单列表
     */
    @Select("SELECT * FROM payment_order WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<PaymentOrder> findByUserId(@Param("userId") String userId);

    /**
     * 插入新订单
     */
    @Insert("INSERT INTO payment_order(id, user_id, package_id, amount, payment_method, status, expiry_time) " +
            "VALUES(#{id}, #{userId}, #{packageId}, #{amount}, #{paymentMethod}, #{status}, #{expiryTime})")
    int insert(PaymentOrder order);

    /**
     * 更新订单状态
     */
    @Update("UPDATE payment_order SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") String id, @Param("status") String status);

    /**
     * 更新支付成功信息
     */
    @Update("UPDATE payment_order SET status = 'paid', payment_time = #{paymentTime}, " +
            "transaction_id = #{transactionId}, update_time = NOW() WHERE id = #{id}")
    int updatePaymentSuccess(@Param("id") String id, @Param("paymentTime") java.util.Date paymentTime, @Param("transactionId") String transactionId);

    /**
     * 更新支付失败信息
     */
    @Update("UPDATE payment_order SET status = 'failed', update_time = NOW() WHERE id = #{id}")
    int updatePaymentFailed(@Param("id") String id);

    /**
     * 取消订单
     */
    @Update("UPDATE payment_order SET status = 'cancelled', update_time = NOW() WHERE id = #{id}")
    int cancelOrder(@Param("id") String id);
} 