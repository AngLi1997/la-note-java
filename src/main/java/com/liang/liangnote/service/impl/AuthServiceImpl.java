package com.liang.liangnote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.LoginDTO;
import com.liang.liangnote.dto.LoginResponseDTO;
import com.liang.liangnote.dto.UserInfoDTO;
import com.liang.liangnote.entity.User;
import com.liang.liangnote.mapper.UserMapper;
import com.liang.liangnote.service.AuthService;
import com.liang.liangnote.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 认证服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserMapper userMapper;
    
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Resp<LoginResponseDTO> login(LoginDTO loginDTO) {
        // 直接从数据库查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        // 用户不存在
        if (user == null) {
            return Resp.failed("用户名或密码错误");
        }

        // 用户被禁用
        if (user.getStatus() != null && user.getStatus() == 0) {
            return Resp.failed("用户已被禁用");
        }

        // 密码比对
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Resp.failed("用户名或密码错误");
        }

        // 认证成功，生成JWT令牌
        String role = user.getRole() != null ? user.getRole() : "USER";
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );
        
        final String token = jwtUtil.generateToken(userDetails);

        // 构造返回结果
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        responseDTO.setUserInfo(userInfoDTO);

        return Resp.success(responseDTO);
    }
} 