package com.emotional.companionship.controller;

import com.emotional.companionship.dto.LoginResponseDTO;
import com.emotional.companionship.dto.Result;
import com.emotional.companionship.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "认证接口")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 微信登录
     */
    @PostMapping("/login/wechat")
    @ApiOperation("微信登录")
    public Result<LoginResponseDTO> wxLogin(
            @ApiParam(value = "微信授权返回的临时票据", required = true)
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
    @ApiOperation("检查登录状态")
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