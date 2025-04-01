package com.emotional.companionship.service.impl;

import com.emotional.companionship.dto.ChatSessionDTO;
import com.emotional.companionship.dto.request.EndChatRequest;
import com.emotional.companionship.dto.request.StartChatRequest;
import com.emotional.companionship.entity.ChatSession;
import com.emotional.companionship.entity.DigitalHuman;
import com.emotional.companionship.exception.BusinessException;
import com.emotional.companionship.mapper.ChatSessionMapper;
import com.emotional.companionship.mapper.DigitalHumanMapper;
import com.emotional.companionship.service.ChatSessionService;
import com.emotional.companionship.service.DigitalHumanService;
import com.emotional.companionship.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * 聊天会话服务实现类
 */
@Slf4j
@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private ChatSessionMapper chatSessionMapper;
    
    @Autowired
    private DigitalHumanMapper digitalHumanMapper;
    
    @Autowired
    private DigitalHumanService digitalHumanService;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @Value("${server.port}")
    private String serverPort;

    @Override
    public String buildWebSocketUrl(String sessionId) {
        return "wss://api.emotionalcompanionship.com/ws/chat/" + sessionId;
    }

    @Override
    @Transactional
    public ChatSessionDTO startChat(StartChatRequest request, String userId) {
        // 验证数字人是否存在
        DigitalHuman digitalHuman = digitalHumanMapper.selectByIdAndUserId(request.getDigitalHumanId(), userId);
        if (digitalHuman == null) {
            throw new BusinessException("数字人不存在或不属于当前用户");
        }
        
        // 创建聊天会话
        ChatSession chatSession = new ChatSession();
        chatSession.setId("chat_" + UUID.randomUUID().toString().replace("-", ""));
        chatSession.setUserId(userId);
        chatSession.setDigitalHumanId(request.getDigitalHumanId());
        chatSession.setStartTime(new Date());
        chatSession.setStatus("ongoing");
        chatSession.setCreateTime(new Date());
        chatSession.setUpdateTime(new Date());
        
        int result = chatSessionMapper.insert(chatSession);
        if (result <= 0) {
            throw new BusinessException("创建聊天会话失败");
        }
        
        // 更新数字人最后聊天时间
        digitalHumanService.updateLastChatTime(request.getDigitalHumanId(), new Date());
        
        // 构建WebSocket URL
        String webSocketUrl = buildWebSocketUrl(chatSession.getId());
        
        // 构建响应DTO
        ChatSessionDTO responseDTO = new ChatSessionDTO();
        responseDTO.setSessionId(chatSession.getId());
        responseDTO.setStartTime(DateUtil.formatDateTime(chatSession.getStartTime()));
        responseDTO.setWebSocketUrl(webSocketUrl);
        
        return responseDTO;
    }

    @Override
    @Transactional
    public ChatSessionDTO endChat(EndChatRequest request, String userId) {
        // 验证会话是否存在
        ChatSession chatSession = chatSessionMapper.selectById(request.getSessionId());
        if (chatSession == null) {
            throw new BusinessException("聊天会话不存在");
        }
        
        // 验证会话是否属于当前用户
        if (!chatSession.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此聊天会话");
        }
        
        // 验证会话状态
        if (!"ongoing".equals(chatSession.getStatus())) {
            throw new BusinessException("聊天会话已结束");
        }
        
        // 结束会话
        Date endTime = new Date();
        int duration = DateUtil.calculateSecondsBetween(chatSession.getStartTime(), endTime);
        
        int result = chatSessionMapper.endSession(request.getSessionId(), endTime, duration);
        if (result <= 0) {
            throw new BusinessException("结束聊天会话失败");
        }
        
        // 重新查询会话
        chatSession = chatSessionMapper.selectById(request.getSessionId());
        
        // 构建响应DTO
        ChatSessionDTO responseDTO = new ChatSessionDTO();
        responseDTO.setSessionId(chatSession.getId());
        responseDTO.setStartTime(DateUtil.formatDateTime(chatSession.getStartTime()));
        responseDTO.setEndTime(DateUtil.formatDateTime(chatSession.getEndTime()));
        responseDTO.setDuration(chatSession.getDuration());
        
        return responseDTO;
    }

    @Override
    public int getTotalChatMinutes(String userId) {
        int totalSeconds = chatSessionMapper.getTotalChatDurationByUserId(userId);
        // 将秒转换为分钟，向上取整
        return (int) Math.ceil(totalSeconds / 60.0);
    }
    
    @Override
    public ChatSession getSessionById(String id) {
        return chatSessionMapper.selectById(id);
    }
    
    @Override
    public boolean isSessionBelongToUser(String id, String userId) {
        ChatSession session = chatSessionMapper.selectById(id);
        return session != null && session.getUserId().equals(userId);
    }
    
    @Override
    public boolean hasOngoingSession(String userId) {
        ChatSession ongoingSession = chatSessionMapper.findOngoingSessionByUserId(userId);
        return ongoingSession != null;
    }
    
    @Override
    public ChatSession getOngoingSession(String userId) {
        return chatSessionMapper.findOngoingSessionByUserId(userId);
    }
} 