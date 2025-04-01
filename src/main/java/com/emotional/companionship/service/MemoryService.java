package com.emotional.companionship.service;

import com.emotional.companionship.dto.request.CreateMemoryRequest;
import com.emotional.companionship.dto.MemoryDTO;
import com.emotional.companionship.common.PageResultDTO;

/**
 * 记忆服务接口
 */
public interface MemoryService {
    /**
     * 获取记忆列表
     */
    PageResultDTO<MemoryDTO> getMemories(String userId, Integer page, Integer size, String digitalHumanId);
    
    /**
     * 获取记忆详情
     */
    MemoryDTO getMemoryById(String id, String userId);
    
    /**
     * 创建记忆
     */
    MemoryDTO createMemory(CreateMemoryRequest request, String userId);
    
    /**
     * 删除记忆
     */
    void deleteMemory(String id, String userId);
} 