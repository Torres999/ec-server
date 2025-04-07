package com.emotional.companionship.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emotional.companionship.common.Result;
import com.emotional.companionship.dto.DigitalHumanCreateDTO;
import com.emotional.companionship.dto.DigitalHumanDTO;
import com.emotional.companionship.dto.request.CreateDigitalHumanRequest;
import com.emotional.companionship.dto.request.UpdateDigitalHumanRequest;
import com.emotional.companionship.service.DigitalHumanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * 数字人控制器
 */
@Slf4j
@RestController
@RequestMapping("/digital-humans")
@Tag(name = "数字人管理")
public class DigitalHumanController {

    @Autowired
    private DigitalHumanService digitalHumanService;

    @GetMapping
    @Operation(summary = "获取数字人列表")
    public Result<List<DigitalHumanDTO>> getDigitalHumans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<DigitalHumanDTO> digitalHumans = digitalHumanService.getDigitalHumanList(userId);
        return Result.success(digitalHumans);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取数字人详情")
    public Result<DigitalHumanDTO> getDigitalHumanDetail(
            @Parameter(description = "数字人ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        DigitalHumanDTO digitalHuman = digitalHumanService.getDigitalHumanDetail(id, userId);
        return Result.success(digitalHuman);
    }

    @PostMapping
    @Operation(summary = "创建数字人")
    public Result<DigitalHumanDTO> createDigitalHuman(
            @Parameter(description = "创建数字人请求", required = true) @Valid @RequestBody CreateDigitalHumanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // 转换请求对象为DTO
        DigitalHumanCreateDTO createDTO = new DigitalHumanCreateDTO();
        BeanUtils.copyProperties(request, createDTO);

        DigitalHumanDTO digitalHuman = digitalHumanService.createDigitalHuman(createDTO, userId);
        return Result.success("创建成功", digitalHuman);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新数字人信息")
    public Result<DigitalHumanDTO> updateDigitalHuman(
            @Parameter(description = "数字人ID", required = true) @PathVariable String id,
            @Parameter(description = "更新数字人请求", required = true) @Valid @RequestBody UpdateDigitalHumanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // 转换请求对象为DTO
        DigitalHumanCreateDTO updateDTO = new DigitalHumanCreateDTO();
        BeanUtils.copyProperties(request, updateDTO);

        DigitalHumanDTO digitalHuman = digitalHumanService.updateDigitalHuman(id, updateDTO, userId);
        return Result.success("更新成功", digitalHuman);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除数字人")
    public Result<String> deleteDigitalHuman(
            @Parameter(description = "数字人ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boolean success = digitalHumanService.deleteDigitalHuman(id, userId);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.fail(500, "删除失败");
        }
    }
} 