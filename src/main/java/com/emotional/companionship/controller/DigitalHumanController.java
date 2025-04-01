package com.emotional.companionship.controller;

import com.emotional.companionship.common.ApiResponse;
import com.emotional.companionship.dto.request.CreateDigitalHumanRequest;
import com.emotional.companionship.dto.DigitalHumanCreateDTO;
import com.emotional.companionship.dto.DigitalHumanDTO;
import com.emotional.companionship.dto.request.UpdateDigitalHumanRequest;
import com.emotional.companionship.service.DigitalHumanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数字人控制器
 */
@Slf4j
@RestController
@RequestMapping("/digital-humans")
@Api(tags = "数字人管理")
public class DigitalHumanController {

    @Autowired
    private DigitalHumanService digitalHumanService;

    @GetMapping
    @ApiOperation("获取数字人列表")
    public ApiResponse<List<DigitalHumanDTO>> getDigitalHumans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<DigitalHumanDTO> digitalHumans = digitalHumanService.getDigitalHumanList(userId);
        return ApiResponse.success(digitalHumans);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取数字人详情")
    public ApiResponse<DigitalHumanDTO> getDigitalHumanDetail(
            @ApiParam(value = "数字人ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        DigitalHumanDTO digitalHuman = digitalHumanService.getDigitalHumanDetail(id, userId);
        return ApiResponse.success(digitalHuman);
    }

    @PostMapping
    @ApiOperation("创建数字人")
    public ApiResponse<DigitalHumanDTO> createDigitalHuman(
            @ApiParam(value = "创建数字人请求", required = true) @Valid @RequestBody CreateDigitalHumanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // 转换请求对象为DTO
        DigitalHumanCreateDTO createDTO = new DigitalHumanCreateDTO();
        BeanUtils.copyProperties(request, createDTO);

        DigitalHumanDTO digitalHuman = digitalHumanService.createDigitalHuman(createDTO, userId);
        return ApiResponse.success("创建成功", digitalHuman);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新数字人信息")
    public ApiResponse<DigitalHumanDTO> updateDigitalHuman(
            @ApiParam(value = "数字人ID", required = true) @PathVariable String id,
            @ApiParam(value = "更新数字人请求", required = true) @Valid @RequestBody UpdateDigitalHumanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // 转换请求对象为DTO
        DigitalHumanCreateDTO updateDTO = new DigitalHumanCreateDTO();
        BeanUtils.copyProperties(request, updateDTO);

        DigitalHumanDTO digitalHuman = digitalHumanService.updateDigitalHuman(id, updateDTO, userId);
        return ApiResponse.success("更新成功", digitalHuman);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数字人")
    public ApiResponse<String> deleteDigitalHuman(
            @ApiParam(value = "数字人ID", required = true) @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boolean success = digitalHumanService.deleteDigitalHuman(id, userId);
        if (success) {
            return ApiResponse.success("删除成功");
        } else {
            return ApiResponse.success("删除失败");
        }
    }
} 