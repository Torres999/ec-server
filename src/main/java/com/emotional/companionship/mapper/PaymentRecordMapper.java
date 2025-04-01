package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.PaymentRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 充值记录Mapper接口
 */
@Mapper
public interface PaymentRecordMapper {

    /**
     * 根据ID查询记录
     */
    @Select("SELECT * FROM payment_record WHERE id = #{id}")
    PaymentRecord findById(@Param("id") String id);

    /**
     * 根据订单ID查询记录
     */
    @Select("SELECT * FROM payment_record WHERE order_id = #{orderId}")
    PaymentRecord findByOrderId(@Param("orderId") String orderId);

    /**
     * 根据用户ID查询记录列表
     */
    @Select("SELECT * FROM payment_record WHERE user_id = #{userId} ORDER BY payment_time DESC LIMIT #{offset}, #{size}")
    List<PaymentRecord> findByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 插入新记录
     */
    @Insert("INSERT INTO payment_record(id, order_id, user_id, package_name, amount, payment_method, status, payment_time, validity_start, validity_end) " +
            "VALUES(#{id}, #{orderId}, #{userId}, #{packageName}, #{amount}, #{paymentMethod}, #{status}, #{paymentTime}, #{validityStart}, #{validityEnd})")
    int insert(PaymentRecord record);

    /**
     * 计算用户充值记录总数
     */
    @Select("SELECT COUNT(*) FROM payment_record WHERE user_id = #{userId}")
    int countByUserId(@Param("userId") String userId);
} 