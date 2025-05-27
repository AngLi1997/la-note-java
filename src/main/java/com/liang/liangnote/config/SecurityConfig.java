package com.liang.liangnote.config;

import com.liang.liangnote.security.JwtAuthenticationEntryPoint;
import com.liang.liangnote.security.JwtAuthenticationFilter;
import com.liang.liangnote.security.JwtUserDetailsService;
import com.liang.liangnote.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置AuthenticationManager使用我们的自定义UserDetailsService和密码编码器
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUserDetailsService, jwtUtil);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 禁用CSRF（因为使用JWT，不需要防御CSRF）
                .csrf().disable()
                // 配置认证异常处理
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权请求配置
                .authorizeRequests()
                // 允许访问登录接口和Swagger相关接口
                .antMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/v2/api-docs/**", "/webjars/**").permitAll()
                // 所有其他请求需要认证
                .anyRequest().authenticated();

        // 添加JWT过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
} 