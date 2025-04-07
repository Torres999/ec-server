package com.emotional.companionship.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emotional.companionship.common.PageResultDTO;
import com.emotional.companionship.common.Result;
import com.emotional.companionship.dto.MemoryDTO;
import com.emotional.companionship.dto.request.CreateMemoryRequest;
import com.emotional.companionship.service.MemoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * 记忆控制器
 */
@Slf4j
@RestController
@RequestMapping("/memories")
@Tag(name = "记忆管理")
public class MemoryController {

    @Autowired
    private MemoryService memoryService;

    @GetMapping
    @Operation(summary = "获取记忆列表")
    public Result<PageResultDTO<MemoryDTO>> getMemories(
            @Parameter(description = "页码", example = "1") @RequestParam(required = false) Integer page,
            @Parameter(description = "每页大小", example = "20") @RequestParam(required = false) Integer size,
            @Parameter(description = "数字人ID") @RequestParam(required = false) String digitalHumanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        PageResultDTO<MemoryDTO> result = memoryService.getMemories(userId, page, size, digitalHumanId);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取记忆详情")
    public Result<MemoryDTO> getMemoryDetail(
            @Parameter(description = "记忆ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        MemoryDTO memory = memoryService.getMemoryById(id, userId);
        return Result.success(memory);
    }

    @PostMapping
    @Operation(summary = "创建记忆")
    public Result<MemoryDTO> createMemory(
            @Parameter(description = "创建记忆请求", required = true) @Valid @RequestBody CreateMemoryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        MemoryDTO memory = memoryService.createMemory(request, userId);
        return Result.success("创建成功", memory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除记忆")
    public Result<String> deleteMemory(
            @Parameter(description = "记忆ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        memoryService.deleteMemory(id, userId);
        return Result.success("删除成功");
    }
} 