package com.emotional.companionship.dto.request;

import com.emotional.companionship.dto.UserDTO;
import lombok.Data;

/**
 * 登录响应数据传输对象
 */
@Data
public class LoginResponseDTO {
    /**
     * JWT Token
     */
    private String token;
    
    /**
     * 用户信息
     */
    private UserDTO user;
} 