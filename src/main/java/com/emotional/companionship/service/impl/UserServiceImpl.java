package com.emotional.companionship.service.impl;

import com.emotional.companionship.dto.request.LoginResponseDTO;
import com.emotional.companionship.dto.UserDTO;
import com.emotional.companionship.dto.UserDetailDTO;
import com.emotional.companionship.entity.User;
import com.emotional.companionship.mapper.DigitalHumanMapper;
import com.emotional.companionship.mapper.ChatSessionMapper;
import com.emotional.companionship.mapper.UserMapper;
import com.emotional.companionship.security.JwtTokenProvider;
import com.emotional.companionship.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DigitalHumanMapper digitalHumanMapper;

    @Autowired
    private ChatSessionMapper chatSessionMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.app-secret}")
    private String appSecret;

    /**
     * 微信登录
     */
    @Override
    public LoginResponseDTO wxLogin(String code) {
        // 获取微信openid
        String openId = getWxOpenId(code);
        
        // 查询用户是否存在
        User user = userMapper.findByOpenId(openId);
        
        // 如果用户不存在，创建新用户
        if (user == null) {
            // 获取微信用户信息
            Map<String, String> wxUserInfo = getWxUserInfo(openId);
            String nickname = wxUserInfo.getOrDefault("nickname", "用户" + UUID.randomUUID().toString().substring(0, 6));
            String avatarUrl = wxUserInfo.getOrDefault("headimgurl", "");
            
            // 创建新用户
            user = createUser(openId, nickname, avatarUrl);
        }
        
        // 生成JWT令牌
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getId())
                .password("{noop}dummy")
                .authorities("ROLE_USER")
                .build();
        
        String token = jwtTokenProvider.generateToken(userDetails);
        
        // 构建登录响应
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setUser(getUserDTO(user.getId()));
        
        return responseDTO;
    }

    /**
     * 获取微信OpenID
     */
    private String getWxOpenId(String code) {
        // 微信接口地址
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        
        // 发送请求
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        
        // 模拟环境，实际会从微信返回值中获取
        // return (String) response.get("openid");
        return "wx_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    /**
     * 获取微信用户信息
     */
    private Map<String, String> getWxUserInfo(String openId) {
        // 实际项目中会调用微信接口获取用户信息
        // 这里模拟返回结果
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("nickname", "用户" + openId.substring(openId.length() - 6));
        userInfo.put("headimgurl", "https://example.com/avatar.jpg");
        return userInfo;
    }

    /**
     * 根据ID获取用户信息
     */
    @Override
    public User getUserById(String id) {
        return userMapper.findById(id);
    }

    /**
     * 获取用户基本信息DTO
     */
    @Override
    public UserDTO getUserDTO(String id) {
        User user = getUserById(id);
        if (user == null) {
            return null;
        }
        
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * 获取用户详细信息DTO
     */
    @Override
    public UserDetailDTO getUserDetailDTO(String id) {
        User user = getUserById(id);
        if (user == null) {
            return null;
        }
        
        UserDetailDTO detailDTO = new UserDetailDTO();
        BeanUtils.copyProperties(user, detailDTO);
        
        // 设置数字人总数
        detailDTO.setTotalDigitalHumans(digitalHumanMapper.countByUserId(id));
        
        // 设置聊天总分钟数
        detailDTO.setTotalChatMinutes(chatSessionMapper.getTotalChatDurationByUserId(id) / 60); // 转换为分钟
        
        return detailDTO;
    }

    /**
     * 检查登录状态
     */
    @Override
    public boolean checkLoginStatus(String userId) {
        return userMapper.findById(userId) != null;
    }

    /**
     * 更新用户VIP信息
     */
    @Override
    @Transactional
    public void updateUserVip(String userId, Integer vipLevel, Date vipExpireDate) {
        userMapper.updateVipInfo(userId, vipLevel, vipExpireDate);
    }

    /**
     * 增加用户余额
     */
    @Override
    @Transactional
    public void increaseBalance(String userId, BigDecimal amount) {
        User user = getUserById(userId);
        if (user != null) {
            BigDecimal newBalance = user.getBalance().add(amount);
            userMapper.updateBalance(userId, newBalance);
        }
    }

    /**
     * 减少用户余额
     */
    @Override
    @Transactional
    public boolean decreaseBalance(String userId, BigDecimal amount) {
        User user = getUserById(userId);
        if (user != null && user.getBalance().compareTo(amount) >= 0) {
            BigDecimal newBalance = user.getBalance().subtract(amount);
            userMapper.updateBalance(userId, newBalance);
            return true;
        }
        return false;
    }

    /**
     * 创建新用户
     */
    @Override
    @Transactional
    public User createUser(String openId, String nickname, String avatarUrl) {
        // 生成User ID
        String userId = generateUserId();
        
        // 创建用户
        User user = new User();
        user.setId(openId);
        user.setUserId(userId);
        user.setName(nickname);
        user.setDescription("情感陪伴用户");
        user.setAvatar(avatarUrl);
        user.setRegisterDate(new Date());
        user.setVipLevel(0);
        user.setBalance(BigDecimal.ZERO);
        
        // 插入用户
        userMapper.insert(user);
        
        return user;
    }

    /**
     * 生成用户ID
     */
    private String generateUserId() {
        // 简单实现：生成6位随机数字
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
} 