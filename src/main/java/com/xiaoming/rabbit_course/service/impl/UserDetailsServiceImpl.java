package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoming.rabbit_course.entity.User;
import com.xiaoming.rabbit_course.mapper.UserMapper;
import com.xiaoming.rabbit_course.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    /**
     * 登录功能
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getUsername,s);
        User user = userMapper.selectLogin(s);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.isNotBlank(user.getRole())) {
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
        }
//        if ("12345678910".equals(s)) authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new org.springframework.security.core.userdetails.User(s, user.getPassword(), user.getStatus() == 0, true, true, true, authorities);
    }
}
