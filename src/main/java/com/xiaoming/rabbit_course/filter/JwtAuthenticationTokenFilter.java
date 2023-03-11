package com.xiaoming.rabbit_course.filter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoming.rabbit_course.entity.User;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.service.UserService;
import com.xiaoming.rabbit_course.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(token)){
            //放行  //后面有其他过滤器来判断是否有权限访问
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //解析token
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String username = claims.getSubject();
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername,username);
            User user = userService.getOne(queryWrapper);
            List<SimpleGrantedAuthority> authorities=new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
//            if ("12345678910".equals(username)) authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("token无效");
            log.error(e.getMessage());
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
