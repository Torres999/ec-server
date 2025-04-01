package com.emotional.companionship.service.impl;

import com.emotional.companionship.dto.request.CreateMemoryRequest;
import com.emotional.companionship.dto.MemoryDTO;
import com.emotional.companionship.common.PageResultDTO;
import com.emotional.companionship.entity.DigitalHuman;
import com.emotional.companionship.entity.Memory;
import com.emotional.companionship.exception.BusinessException;
import com.emotional.companionship.mapper.DigitalHumanMapper;
import com.emotional.companionship.mapper.MemoryMapper;
import com.emotional.companionship.service.MemoryService;
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
 * 记忆服务实现类
 */
@Slf4j
@Service
public class MemoryServiceImpl implements MemoryService {

    @Autowired
    private MemoryMapper memoryMapper;
    
    @Autowired
    private DigitalHumanMapper digitalHumanMapper;

    @Override
    public PageResultDTO<MemoryDTO> getMemories(String userId, Integer page, Integer size, String digitalHumanId) {
        // 参数处理
        page = (page == null || page < 1) ? 1 : page;
        size = (size == null || size < 1) ? 20 : size;
        int offset = (page - 1) * size;
        
        // 查询条件处理
        List<Memory> memories;
        int total;
        if (digitalHumanId != null && !digitalHumanId.isEmpty()) {
            // 验证数字人是否属于当前用户
            DigitalHuman digitalHuman = digitalHumanMapper.selectByIdAndUserId(digitalHumanId, userId);
            if (digitalHuman == null) {
                throw new BusinessException("数字人不存在或不属于当前用户");
            }
            
            memories = memoryMapper.selectByDigitalHumanId(digitalHumanId, offset, size);
            total = memoryMapper.countByDigitalHumanId(digitalHumanId);
        } else {
            memories = memoryMapper.selectByUserId(userId, offset, size);
            total = memoryMapper.countByUserId(userId);
        }
        
        // 转换为DTO
        List<MemoryDTO> memoryDTOs = memories.stream().map(this::convertToDTO).collect(Collectors.toList());
        
        // 构建分页结果
        PageResultDTO<MemoryDTO> pageResult = new PageResultDTO<>();
        pageResult.setTotal(total);
        pageResult.setList(memoryDTOs);
        pageResult.setPage(page);
        pageResult.setSize(size);
        
        return pageResult;
    }

    @Override
    public MemoryDTO getMemoryById(String id, String userId) {
        Memory memory = memoryMapper.selectById(id);
        if (memory == null || !memory.getUserId().equals(userId)) {
            throw new BusinessException("记忆不存在或不属于当前用户");
        }
        
        MemoryDTO dto = convertToDTO(memory);
        
        // 加入数字人简要信息
        DigitalHuman digitalHuman = digitalHumanMapper.selectById(memory.getDigitalHumanId());
        if (digitalHuman != null) {
            MemoryDTO.DigitalHumanSimpleDTO digitalHumanDTO = new MemoryDTO.DigitalHumanSimpleDTO();
            digitalHumanDTO.setId(digitalHuman.getId());
            digitalHumanDTO.setName(digitalHuman.getName());
            digitalHumanDTO.setRelation(digitalHuman.getRelation());
            dto.setDigitalHuman(digitalHumanDTO);
        }
        
        return dto;
    }

    @Override
    @Transactional
    public MemoryDTO createMemory(CreateMemoryRequest request, String userId) {
        // 验证数字人是否属于当前用户
        DigitalHuman digitalHuman = digitalHumanMapper.selectByIdAndUserId(request.getDigitalHumanId(), userId);
        if (digitalHuman == null) {
            throw new BusinessException("数字人不存在或不属于当前用户");
        }
        
        Memory memory = new Memory();
        memory.setId(UUID.randomUUID().toString().replace("-", ""));
        memory.setUserId(userId);
        memory.setDigitalHumanId(request.getDigitalHumanId());
        memory.setTitle(request.getTitle());
        memory.setContent(request.getContent());
        memory.setMemoryDate(new Date());
        memory.setImageUrl(request.getImageUrl());
        memory.setCreateTime(new Date());
        memory.setUpdateTime(new Date());
        
        int result = memoryMapper.insert(memory);
        if (result <= 0) {
            throw new BusinessException("创建记忆失败");
        }
        
        return convertToDTO(memory);
    }

    @Override
    @Transactional
    public void deleteMemory(String id, String userId) {
        Memory memory = memoryMapper.selectById(id);
        if (memory == null) {
            throw new BusinessException("记忆不存在");
        }
        
        if (!memory.getUserId().equals(userId)) {
            throw new BusinessException("无权删除此记忆");
        }
        
        int result = memoryMapper.deleteById(id);
        if (result <= 0) {
            throw new BusinessException("删除记忆失败");
        }
    }

    /**
     * 将实体转换为DTO
     */
    private MemoryDTO convertToDTO(Memory memory) {
        MemoryDTO dto = new MemoryDTO();
        BeanUtils.copyProperties(memory, dto);
        
        // 处理日期格式
        if (memory.getMemoryDate() != null) {
            dto.setDate(DateUtil.formatDateTime(memory.getMemoryDate()));
        }
        
        return dto;
    }
} 