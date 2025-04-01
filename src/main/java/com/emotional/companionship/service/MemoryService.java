package com.emotional.companionship.service;

import com.emotional.companionship.entity.Memory;

import java.util.List;
import java.util.Map;

/**
 * 记忆服务接口
 */
public interface MemoryService {

    /**
     * 获取记忆列表
     */
    Map<String, Object> getMemoryList(String userId, Integer page, Integer size, String digitalHumanId);

    /**
     * 保存记忆
     */
    Memory saveMemory(String title, String content, String userId, String digitalHumanId, String imageUrl);

    /**
     * 获取记忆详情
     */
    Memory getMemoryDetail(String id, String userId);

    /**
     * 删除记忆
     */
    boolean deleteMemory(String id, String userId);

    /**
     * 检查记忆是否属于用户
     */
    boolean isMemoryBelongToUser(String id, String userId);

    /**
     * 根据ID获取记忆
     */
    Memory getById(String id);
} 