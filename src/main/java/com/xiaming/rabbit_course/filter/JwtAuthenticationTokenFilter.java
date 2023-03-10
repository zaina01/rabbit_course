package com.xiaming.rabbit_course.filter;

import com.xiaming.rabbit_course.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)){
            //放行  //后面又其他过滤器来判断是否有权限访问
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //解析token
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String phone = claims.getSubject();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(phone,null,null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
