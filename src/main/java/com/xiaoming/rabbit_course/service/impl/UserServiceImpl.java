package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.User;
import com.xiaoming.rabbit_course.mapper.UserMapper;
import com.xiaoming.rabbit_course.service.UserService;
import com.xiaoming.rabbit_course.utils.ConcealDataUtils;
import com.xiaoming.rabbit_course.utils.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private AuthenticationManager authenticationManager;

    //    @Resource
//    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public Result<Map<String, String>> login(User user) {
        //AuthenticationManager 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过抛出异常
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，使用userId生成一个jwt
        org.springframework.security.core.userdetails.User loginUser = (org.springframework.security.core.userdetails.User) authenticate.getPrincipal();
        String username = loginUser.getUsername();
        String token = JwtUtil.createJWT(username,60*60*1000L*24);
//        //把用户信息存到redis userId 作为key
//        stringRedisTemplate.opsForValue().set("login",token);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        //返回jwt
        return Result.ok("登录成功", map);
    }

    /**
     * 用户退出登录
     * @return
     */
    @Override
    public Result<String> logout() {
        //删除SecurityContextHolder中的用户
        SecurityContextHolder.getContext().setAuthentication(null);
        return Result.ok("退出成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Result<String> signIn(User user) {
        user.setAvatar("100000000000000001.jpg");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (save(user)) {
            return Result.ok("注册成功");
        }
        return Result.error("注册失败");
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public Result<String> serviceUpdateById(User user) {
        if (user.getId() != null) {
            if (StringUtils.isNotBlank(user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            if (updateById(user)) {
                return Result.ok("修改成功");
            }
        }
        return Result.error("修改失败");
    }

    /**
     * 用户查询个人信息
     * @param username
     * @return
     */
    @Override
    public Result<User> findByusername(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = getOne(lambdaQueryWrapper);
        if (user != null) {
//            user.setPassword("");
            return Result.ok("查询成功", user);
        }
        return Result.error("查询失败");
    }

    /**
     * 管理员根据id查用户信息
     * @param id
     * @return
     */
    @Override
    public Result<User> findById(Long id) {
        User user = getById(id);
        if (user != null) {
//            user.setPassword("");
            return Result.ok("查询成功", user);
        }
        return Result.error("查询失败");
    }

    /**
     * 用户信息分页查询
     * @param page
     * @param size
     * @param username
     * @return
     */
    @Override
    public Result<Page> findAll(int page, int size,String username) {
        Page<User> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(User::getRole,"ROLE_ADMIN");
        queryWrapper.eq(StringUtils.isNotBlank(username),User::getUsername,username);
        Page<User> userPage = page(pageInfo,queryWrapper);
        return Result.ok("查询成功",userPage);
    }

}
