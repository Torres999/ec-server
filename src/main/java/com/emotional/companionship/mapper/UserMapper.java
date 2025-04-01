package com.emotional.companionship.mapper;

import com.emotional.companionship.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {

    /**
     * 根据ID查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") String id);

    /**
     * 根据微信OpenID查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{openId}")
    User findByOpenId(@Param("openId") String openId);

    /**
     * 插入新用户
     */
    @Insert("INSERT INTO user(id, user_id, name, description, avatar, email, register_date, vip_level, vip_expire_date, balance) " +
            "VALUES(#{id}, #{userId}, #{name}, #{description}, #{avatar}, #{email}, #{registerDate}, #{vipLevel}, #{vipExpireDate}, #{balance})")
    int insert(User user);

    /**
     * 更新用户信息
     */
    @Update("UPDATE user SET name = #{name}, description = #{description}, avatar = #{avatar}, " +
            "email = #{email}, vip_level = #{vipLevel}, vip_expire_date = #{vipExpireDate}, " +
            "balance = #{balance}, update_time = NOW() WHERE id = #{id}")
    int update(User user);

    /**
     * 更新用户VIP信息
     */
    @Update("UPDATE user SET vip_level = #{vipLevel}, vip_expire_date = #{vipExpireDate}, " +
            "update_time = NOW() WHERE id = #{id}")
    int updateVipInfo(@Param("id") String id, @Param("vipLevel") Integer vipLevel, @Param("vipExpireDate") java.util.Date vipExpireDate);

    /**
     * 更新用户余额
     */
    @Update("UPDATE user SET balance = #{balance}, update_time = NOW() WHERE id = #{id}")
    int updateBalance(@Param("id") String id, @Param("balance") java.math.BigDecimal balance);
} 