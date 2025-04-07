package com.emotional.companionship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emotional.companionship.common.Result;
import com.emotional.companionship.dto.UserDTO;
import com.emotional.companionship.dto.UserDetailDTO;
import com.emotional.companionship.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/profile")
    @Operation(summary = "获取当前登录用户的基本信息")
    public Result<UserDTO> getUserProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();
            
            UserDTO userDTO = userService.getUserDTO(userId);
            if (userDTO != null) {
                return Result.success("获取成功", userDTO);
            } else {
                return Result.fail(404, "用户不存在");
            }
        } catch (Exception e) {
            return Result.fail(500, "获取用户信息失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户详细信息
     */
    @GetMapping("/details")
    @Operation(summary = "获取当前登录用户的详细信息")
    public Result<UserDetailDTO> getUserDetails() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();
            
            UserDetailDTO detailDTO = userService.getUserDetailDTO(userId);
            if (detailDTO != null) {
                return Result.success("获取成功", detailDTO);
            } else {
                return Result.fail(404, "用户不存在");
            }
        } catch (Exception e) {
            return Result.fail(500, "获取用户详细信息失败：" + e.getMessage());
        }
    }
} 