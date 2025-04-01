package com.emotional.companionship.service;

import com.emotional.companionship.dto.DigitalHumanDTO;
import com.emotional.companionship.dto.DigitalHumanCreateDTO;
import com.emotional.companionship.entity.DigitalHuman;

import java.util.List;

/**
 * 数字人服务接口
 */
public interface DigitalHumanService {

    /**
     * 获取数字人列表
     */
    List<DigitalHumanDTO> getDigitalHumanList(String userId);

    /**
     * 根据ID获取数字人详情
     */
    DigitalHumanDTO getDigitalHumanDetail(String id, String userId);

    /**
     * 创建数字人
     */
    DigitalHumanDTO createDigitalHuman(DigitalHumanCreateDTO createDTO, String userId);

    /**
     * 更新数字人信息
     */
    DigitalHumanDTO updateDigitalHuman(String id, DigitalHumanCreateDTO updateDTO, String userId);

    /**
     * 删除数字人
     */
    boolean deleteDigitalHuman(String id, String userId);

    /**
     * 更新数字人最后聊天时间
     */
    void updateLastChatTime(String id, java.util.Date lastChatTime);

    /**
     * 检查数字人是否属于用户
     */
    boolean isDigitalHumanBelongToUser(String id, String userId);

    /**
     * 根据ID获取数字人实体
     */
    DigitalHuman getById(String id);
} 