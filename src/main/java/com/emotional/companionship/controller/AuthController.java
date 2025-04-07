package com.emotional.companionship.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emotional.companionship.common.Result;
import com.emotional.companionship.dto.request.LoginResponseDTO;
import com.emotional.companionship.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 微信登录
     */
    @PostMapping("/login/wechat")
    @Operation(summary = "微信登录")
    public Result<LoginResponseDTO> wxLogin(
            @Parameter(description = "微信授权返回的临时票据", required = true)
            @RequestParam String code) {
        try {
            LoginResponseDTO loginResponse = userService.wxLogin(code);
            return Result.success("登录成功", loginResponse);
        } catch (Exception e) {
            return Result.fail(500, "登录失败：" + e.getMessage());
        }
    }

    /**
     * 检查登录状态
     */
    @GetMapping("/check")
    @Operation(summary = "检查登录状态")
    public Result<Map<String, Object>> checkLoginStatus() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            // 判断是否已认证
            if (authentication != null && authentication.isAuthenticated() && 
                    !"anonymousUser".equals(authentication.getPrincipal().toString())) {
                String userId = authentication.getName();
                
                // 检查用户是否存在
                boolean isLoggedIn = userService.checkLoginStatus(userId);
                
                Map<String, Object> result = new HashMap<>();
                result.put("isLoggedIn", isLoggedIn);
                result.put("userId", userId);
                
                return Result.success("登录状态有效", result);
            } else {
                Map<String, Object> result = new HashMap<>();
                result.put("isLoggedIn", false);
                result.put("userId", null);
                
                return Result.success("未登录", result);
            }
        } catch (Exception e) {
            return Result.fail(500, "检查登录状态失败：" + e.getMessage());
        }
    }
} 