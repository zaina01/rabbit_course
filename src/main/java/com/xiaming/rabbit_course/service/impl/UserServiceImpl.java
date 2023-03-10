package com.xiaming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaming.rabbit_course.common.Result;
import com.xiaming.rabbit_course.entity.User;
import com.xiaming.rabbit_course.mapper.UserMapper;
import com.xiaming.rabbit_course.service.UserService;
import com.xiaming.rabbit_course.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private AuthenticationManager authenticationManager;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result<Map<String,String>> login(User user) {
        //AuthenticationManager 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getPhone(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过抛出异常
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，使用userId生成一个jwt
        org.springframework.security.core.userdetails.User loginUser = (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();
        String phone = loginUser.getUsername();
        String token = JwtUtil.createJWT(phone);
//        //把用户信息存到redis userId 作为key
//        stringRedisTemplate.opsForValue().set("login",token);
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        //返回jwt
        return Result.ok("登录成功",map);
    }
}
