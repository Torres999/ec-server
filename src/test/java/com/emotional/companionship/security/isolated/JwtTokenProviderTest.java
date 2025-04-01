package com.emotional.companionship.security.isolated;

import java.util.Date;

/**
 * 简单的JWT令牌提供者验证程序
 */
public class JwtTokenProviderTest {
    public static void main(String[] args) {
        // 创建JwtTokenProvider实例
        JwtTokenProviderImpl jwtTokenProvider = new JwtTokenProviderImpl();
        jwtTokenProvider.init();
        
        // 测试用户名
        String username = "testuser";
        
        // 测试生成token
        System.out.println("===== 测试JWT令牌生成 =====");
        String token = jwtTokenProvider.generateToken(username);
        System.out.println("生成的令牌: " + token);
        
        // 测试从token提取用户名
        System.out.println("\n===== 测试从令牌提取用户名 =====");
        String extractedUsername = jwtTokenProvider.getUsernameFromToken(token);
        System.out.println("从令牌提取的用户名: " + extractedUsername);
        System.out.println("验证用户名是否匹配: " + extractedUsername.equals(username));
        
        // 测试令牌验证
        System.out.println("\n===== 测试令牌验证 =====");
        boolean isValid = jwtTokenProvider.validateToken(token);
        System.out.println("令牌是否有效: " + isValid);
        
        // 测试过期令牌
        System.out.println("\n===== 测试过期令牌 =====");
        jwtTokenProvider.setJwtExpiration(-3600000); // 设置为负数使令牌立即过期
        String expiredToken = jwtTokenProvider.generateToken(username);
        jwtTokenProvider.setJwtExpiration(3600000); // 恢复正常过期时间
        
        Date expirationDate = jwtTokenProvider.getExpirationDateFromToken(expiredToken);
        System.out.println("过期令牌的过期时间: " + expirationDate);
        System.out.println("过期令牌是否已过期: " + jwtTokenProvider.isTokenExpired(expiredToken));
        System.out.println("过期令牌是否有效: " + jwtTokenProvider.validateToken(expiredToken));
        
        // 测试无效令牌
        System.out.println("\n===== 测试无效令牌 =====");
        String invalidToken = token + "invalid";
        System.out.println("无效令牌是否有效: " + jwtTokenProvider.validateToken(invalidToken));
        
        // 测试空令牌
        System.out.println("\n===== 测试空令牌 =====");
        System.out.println("空令牌是否有效: " + jwtTokenProvider.validateToken(""));
        System.out.println("null令牌是否有效: " + jwtTokenProvider.validateToken(null));
        
        // 测试使用不同密钥验证
        System.out.println("\n===== 测试不同密钥验证 =====");
        String differentSecret = "8Zz5tw0Ionm3XPZZfN0NOml3z9FMfmpgXwovR9fp6ryDIoGRM8EPHAB6iHsc0fc";
        jwtTokenProvider.setJwtSecret(differentSecret);
        System.out.println("使用不同密钥验证原令牌是否有效: " + jwtTokenProvider.validateToken(token));
        
        System.out.println("\n所有测试完成!");
    }
} 