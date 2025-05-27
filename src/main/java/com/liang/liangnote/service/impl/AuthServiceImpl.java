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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Resp<LoginResponseDTO> login(LoginDTO loginDTO) {
        try {
            // 使用Spring Security的AuthenticationManager进行认证
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
        } catch (DisabledException e) {
            return Resp.failed("用户已被禁用");
        } catch (BadCredentialsException e) {
            return Resp.failed("用户名或密码错误");
        }

        // 认证成功，生成JWT令牌
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        // 构造返回结果
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(token);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        responseDTO.setUserInfo(userInfoDTO);

        return Resp.success(responseDTO);
    }
} 