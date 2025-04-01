package com.emotional.companionship.controller;

import com.emotional.companionship.common.Result;
import com.emotional.companionship.dto.request.CreateMemoryRequest;
import com.emotional.companionship.dto.MemoryDTO;
import com.emotional.companionship.common.PageResultDTO;
import com.emotional.companionship.service.MemoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 记忆控制器
 */
@Slf4j
@RestController
@RequestMapping("/memories")
@Api(tags = "记忆管理")
public class MemoryController {

    @Autowired
    private MemoryService memoryService;

    @GetMapping
    @ApiOperation("获取记忆列表")
    public Result<PageResultDTO<MemoryDTO>> getMemories(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(required = false) Integer page,
            @ApiParam(value = "每页大小", defaultValue = "20") @RequestParam(required = false) Integer size,
            @ApiParam(value = "数字人ID") @RequestParam(required = false) String digitalHumanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        PageResultDTO<MemoryDTO> result = memoryService.getMemories(userId, page, size, digitalHumanId);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取记忆详情")
    public Result<MemoryDTO> getMemoryDetail(
            @ApiParam(value = "记忆ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        MemoryDTO memory = memoryService.getMemoryById(id, userId);
        return Result.success(memory);
    }

    @PostMapping
    @ApiOperation("创建记忆")
    public Result<MemoryDTO> createMemory(
            @ApiParam(value = "创建记忆请求", required = true) @Valid @RequestBody CreateMemoryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        MemoryDTO memory = memoryService.createMemory(request, userId);
        return Result.success("创建成功", memory);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除记忆")
    public Result<String> deleteMemory(
            @ApiParam(value = "记忆ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        memoryService.deleteMemory(id, userId);
        return Result.success("删除成功");
    }
} 