package com.emotional.companionship.service;

import com.emotional.companionship.dto.LoginResponseDTO;
import com.emotional.companionship.dto.UserDTO;
import com.emotional.companionship.dto.UserDetailDTO;
import com.emotional.companionship.entity.User;

import java.math.BigDecimal;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 微信登录
     */
    LoginResponseDTO wxLogin(String code);

    /**
     * 根据ID获取用户信息
     */
    User getUserById(String id);

    /**
     * 获取用户基本信息DTO
     */
    UserDTO getUserDTO(String id);

    /**
     * 获取用户详细信息DTO
     */
    UserDetailDTO getUserDetailDTO(String id);

    /**
     * 检查登录状态
     */
    boolean checkLoginStatus(String userId);

    /**
     * 更新用户VIP信息
     */
    void updateUserVip(String userId, Integer vipLevel, java.util.Date vipExpireDate);

    /**
     * 增加用户余额
     */
    void increaseBalance(String userId, BigDecimal amount);

    /**
     * 减少用户余额
     */
    boolean decreaseBalance(String userId, BigDecimal amount);

    /**
     * 创建新用户
     */
    User createUser(String openId, String nickname, String avatarUrl);
} 