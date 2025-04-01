package com.emotional.companionship.security;

import com.emotional.companionship.entity.User;
import com.emotional.companionship.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * UserDetailsService实现类
 * 用于Spring Security认证
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("未找到用户：" + userId);
        }

        // 由于我们的应用是通过微信登录，不需要密码验证，这里设置一个随机密码
        return new org.springframework.security.core.userdetails.User(
                user.getId(),  // 使用用户ID作为用户名
                "{noop}dummy", // 使用一个虚拟密码，前缀{noop}表示不进行加密
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
} 