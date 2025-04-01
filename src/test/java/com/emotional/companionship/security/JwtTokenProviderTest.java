package com.emotional.companionship.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * JwtTokenProvider单元测试
 */
@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private String testSecret = "8Zz5tw0Ionm3XPZZfN0NOml3z9FMfmpgXwovR9fp6ryDIoGRM8EPHAB6iHsc0fb";
    private long testExpiration = 3600000; // 1小时
    private UserDetails testUserDetails;
    private UserDetails testAdminUserDetails;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", testSecret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", testExpiration);
        
        // 初始化key
        Key key = Keys.hmacShaKeyFor(testSecret.getBytes());
        ReflectionTestUtils.setField(jwtTokenProvider, "key", key);
        
        // 创建测试普通用户
        testUserDetails = new User("testuser", "password", new ArrayList<>());
        
        // 创建测试管理员用户
        Collection<GrantedAuthority> authorities = Arrays.asList(
            new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_USER")
        );
        testAdminUserDetails = new User("admin", "adminpassword", authorities);
    }

    @Test
    @DisplayName("测试token生成")
    public void testGenerateToken() {
        String token = jwtTokenProvider.generateToken(testUserDetails);
        System.out.printf("Bearer " + token);
        // 验证生成的令牌不为空
        assertNotNull(token);
        // 验证令牌包含三部分（header.payload.signature）
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length);
    }
    
    @Test
    @DisplayName("测试为不同用户生成的token不同")
    public void testGenerateTokenForDifferentUsers() {
        String userToken = jwtTokenProvider.generateToken(testUserDetails);
        String adminToken = jwtTokenProvider.generateToken(testAdminUserDetails);
        
        // 验证两个令牌不相同
        assertNotEquals(userToken, adminToken);
    }

    @Test
    @DisplayName("测试从token提取用户名")
    public void testGetUsernameFromToken() {
        String token = jwtTokenProvider.generateToken(testUserDetails);
        String username = jwtTokenProvider.getUsernameFromToken(token);
        
        // 验证从令牌中提取的用户名与预期一致
        assertEquals("testuser", username);
    }
    
    @Test
    @DisplayName("测试从管理员token提取用户名")
    public void testGetUsernameFromAdminToken() {
        String token = jwtTokenProvider.generateToken(testAdminUserDetails);
        String username = jwtTokenProvider.getUsernameFromToken(token);
        
        // 验证从令牌中提取的用户名与预期一致
        assertEquals("admin", username);
    }

    @Test
    @DisplayName("测试从token提取过期时间")
    public void testGetExpirationDateFromToken() {
        String token = jwtTokenProvider.generateToken(testUserDetails);
        Date expirationDate = jwtTokenProvider.getExpirationDateFromToken(token);
        
        // 验证过期时间在当前时间之后
        assertTrue(expirationDate.after(new Date()));
        
        // 验证过期时间与预期相符（允许1秒的误差）
        long expectedExpirationTime = System.currentTimeMillis() + testExpiration;
        long actualExpirationTime = expirationDate.getTime();
        assertTrue(Math.abs(expectedExpirationTime - actualExpirationTime) < 1000);
    }

    @Test
    @DisplayName("测试检查token是否过期")
    public void testIsTokenExpired() {
        // 创建一个正常的令牌
        String validToken = jwtTokenProvider.generateToken(testUserDetails);
        
        // 验证正常令牌未过期
        assertFalse(jwtTokenProvider.isTokenExpired(validToken));
        
        // 设置一个已过期的令牌（将过期时间设为负数）
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", -3600000);
        String expiredToken = jwtTokenProvider.generateToken(testUserDetails);
        
        // 恢复正常的过期时间
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", testExpiration);
        
        // 验证过期令牌已过期
        assertTrue(jwtTokenProvider.isTokenExpired(expiredToken));
    }

    @Test
    @DisplayName("测试验证token有效性")
    public void testValidateToken() {
        // 创建一个有效的令牌
        String validToken = jwtTokenProvider.generateToken(testUserDetails);
        
        // 验证有效令牌通过验证
        assertTrue(jwtTokenProvider.validateToken(validToken));
        
        // 创建一个格式错误的令牌
        String invalidToken = validToken + "invalid";
        
        // 验证格式错误的令牌不通过验证
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
    
    @Test
    @DisplayName("测试验证过期token")
    public void testValidateExpiredToken() {
        // 创建一个已过期的令牌
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", -3600000);
        String expiredToken = jwtTokenProvider.generateToken(testUserDetails);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", testExpiration);
        
        // 验证过期令牌不通过验证
        assertFalse(jwtTokenProvider.validateToken(expiredToken));
    }
    
    @Test
    @DisplayName("测试验证格式错误的token")
    public void testValidateInvalidFormatToken() {
        // 创建一个格式错误的令牌
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWI";
        
        // 验证格式错误的令牌不通过验证
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
    
    @Test
    @DisplayName("测试验证空token")
    public void testValidateEmptyToken() {
        // 验证空令牌不通过验证
        assertFalse(jwtTokenProvider.validateToken(""));
        assertFalse(jwtTokenProvider.validateToken(null));
    }

    @Test
    @DisplayName("测试从token提取声明")
    public void testExtractClaim() {
        String token = jwtTokenProvider.generateToken(testUserDetails);
        
        // 提取subject声明
        String subject = jwtTokenProvider.extractClaim(token, Claims::getSubject);
        assertEquals("testuser", subject);
        
        // 提取issuedAt声明
        Date issuedAt = jwtTokenProvider.extractClaim(token, Claims::getIssuedAt);
        assertNotNull(issuedAt);
        assertTrue(Math.abs(issuedAt.getTime() - System.currentTimeMillis()) < 1000);
    }
    
    @Test
    @DisplayName("测试不匹配的签名会导致验证失败")
    public void testTokenWithDifferentSignature() {
        // 先生成一个token
        String token = jwtTokenProvider.generateToken(testUserDetails);
        
        // 修改密钥
        String differentSecret = "8Zz5tw0Ionm3XPZZfN0NOml3z9FMfmpgXwovR9fp6ryDIoGRM8EPHAB6iHsc0fc";
        Key differentKey = Keys.hmacShaKeyFor(differentSecret.getBytes());
        ReflectionTestUtils.setField(jwtTokenProvider, "key", differentKey);
        
        // 验证之前生成的token不能通过验证
        assertFalse(jwtTokenProvider.validateToken(token));
        
        // 恢复原密钥
        Key originalKey = Keys.hmacShaKeyFor(testSecret.getBytes());
        ReflectionTestUtils.setField(jwtTokenProvider, "key", originalKey);
    }
    
    @Test
    @DisplayName("测试初始化方法")
    public void testInit() {
        // 创建一个新实例，不通过反射设置key
        JwtTokenProvider provider = new JwtTokenProvider();
        ReflectionTestUtils.setField(provider, "jwtSecret", testSecret);
        
        // 调用初始化方法
        provider.init();
        
        // 验证key已被初始化
        Key key = (Key) ReflectionTestUtils.getField(provider, "key");
        assertNotNull(key);
        
        // 验证可以正常生成和验证token
        String token = provider.generateToken(testUserDetails);
        assertTrue(provider.validateToken(token));
    }
} 