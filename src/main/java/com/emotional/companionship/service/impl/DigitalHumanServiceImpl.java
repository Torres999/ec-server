package com.emotional.companionship.service.impl;

import com.emotional.companionship.dto.DigitalHumanCreateDTO;
import com.emotional.companionship.dto.DigitalHumanDTO;
import com.emotional.companionship.entity.DigitalHuman;
import com.emotional.companionship.exception.BusinessException;
import com.emotional.companionship.mapper.DigitalHumanMapper;
import com.emotional.companionship.service.DigitalHumanService;
import com.emotional.companionship.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 数字人服务实现类
 */
@Slf4j
@Service
public class DigitalHumanServiceImpl implements DigitalHumanService {

    @Autowired
    private DigitalHumanMapper digitalHumanMapper;

    @Override
    public List<DigitalHumanDTO> getDigitalHumanList(String userId) {
        List<DigitalHuman> digitalHumans = digitalHumanMapper.selectByUserId(userId);
        return digitalHumans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public DigitalHumanDTO getDigitalHumanDetail(String id, String userId) {
        DigitalHuman digitalHuman = digitalHumanMapper.selectByIdAndUserId(id, userId);
        if (digitalHuman == null) {
            throw new BusinessException("数字人不存在或不属于当前用户");
        }
        return convertToDTO(digitalHuman);
    }

    @Override
    @Transactional
    public DigitalHumanDTO createDigitalHuman(DigitalHumanCreateDTO createDTO, String userId) {
        DigitalHuman digitalHuman = new DigitalHuman();
        digitalHuman.setId(UUID.randomUUID().toString().replace("-", ""));
        digitalHuman.setUserId(userId);
        digitalHuman.setName(createDTO.getName());
        digitalHuman.setRelation(createDTO.getRelation());
        digitalHuman.setPersonality(createDTO.getPersonality());
        digitalHuman.setAvatarUrl(createDTO.getAvatarUrl());
        digitalHuman.setLastChatTime(new Date());
        digitalHuman.setCreateTime(new Date());
        digitalHuman.setUpdateTime(new Date());
        
        int result = digitalHumanMapper.insert(digitalHuman);
        
        return convertToDTO(digitalHuman);
    }

    @Override
    @Transactional
    public DigitalHumanDTO updateDigitalHuman(String id, DigitalHumanCreateDTO updateDTO, String userId) {
        DigitalHuman existingDigitalHuman = digitalHumanMapper.selectByIdAndUserId(id, userId);
        if (existingDigitalHuman == null) {
            throw new BusinessException("数字人不存在或不属于当前用户");
        }
        
        if (updateDTO.getName() != null) {
            existingDigitalHuman.setName(updateDTO.getName());
        }
        if (updateDTO.getRelation() != null) {
            existingDigitalHuman.setRelation(updateDTO.getRelation());
        }
        if (updateDTO.getPersonality() != null) {
            existingDigitalHuman.setPersonality(updateDTO.getPersonality());
        }
        if (updateDTO.getAvatarUrl() != null) {
            existingDigitalHuman.setAvatarUrl(updateDTO.getAvatarUrl());
        }
        existingDigitalHuman.setUpdateTime(new Date());
        
        int result = digitalHumanMapper.update(existingDigitalHuman);
        
        return convertToDTO(existingDigitalHuman);
    }

    @Override
    @Transactional
    public boolean deleteDigitalHuman(String id, String userId) {
        DigitalHuman existingDigitalHuman = digitalHumanMapper.selectByIdAndUserId(id, userId);
        if (existingDigitalHuman == null) {
            throw new BusinessException("数字人不存在或不属于当前用户");
        }
        
        int result = digitalHumanMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public void updateLastChatTime(String id, Date lastChatTime) {
        DigitalHuman digitalHuman = digitalHumanMapper.selectById(id);
        if (digitalHuman != null) {
            digitalHumanMapper.updateLastChatTime(id, lastChatTime);
        }
    }
    
    @Override
    public boolean isDigitalHumanBelongToUser(String id, String userId) {
        DigitalHuman digitalHuman = digitalHumanMapper.selectByIdAndUserId(id, userId);
        return digitalHuman != null;
    }
    
    @Override
    public DigitalHuman getById(String id) {
        return digitalHumanMapper.selectById(id);
    }

    /**
     * 将实体转换为DTO
     */
    private DigitalHumanDTO convertToDTO(DigitalHuman digitalHuman) {
        DigitalHumanDTO dto = new DigitalHumanDTO();
        BeanUtils.copyProperties(digitalHuman, dto);
        
        // 格式化日期
        if (digitalHuman.getLastChatTime() != null) {
            dto.setLastChatTime(DateUtil.formatDateTime(digitalHuman.getLastChatTime()));
        }
        
        return dto;
    }
} 