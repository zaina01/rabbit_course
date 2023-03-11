package com.xiaoming.rabbit_course.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.User;
import com.xiaoming.rabbit_course.service.UserService;
import com.xiaoming.rabbit_course.utils.ConcealDataUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private UserService userService;
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result<Map<String,String>> login(@ApiParam("接收登录手机号和密码") @RequestBody User user){
        //登录
        return userService.login(user);
    }
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result<String> login(){
        //登录
        return userService.logout();
    }


    @ApiOperation("注册接口")//接口描述
    @PostMapping("/signIn")
    public Result<String> signIn(@ApiParam("注册信息 用户昵称 手机号 密码不能为空 密码长度应该在6-20之间") @Validated @RequestBody User user) {
        if (StringUtils.isEmpty(user.getAvatar())) user.setAvatar("100000000000000001.jpg");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        log.info("user:{}", user);
        if (userService.save(user)) {
            return Result.ok("注册成功");
        }
        return Result.error("注册失败");
    }
    @ApiOperation("根据id查询用户信息 该接口只有ROLE_ADMIN权限才可访问")
    @Secured("ROLE_ADMIN")
    @GetMapping
    public Result<User> findById(@ApiParam("用户ID") @NotNull(message = "id不能为空") Long id) {
        log.info("id:{}", id);
//        log.info("用户{}调用了查询接口", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword("");
            user.setPhone(ConcealDataUtils.concealPhone(user.getPhone()));
            return Result.ok("查询成功", user);
        }
        return Result.error("查询失败");
    }
    @ApiOperation("用户查询个人信息 该接口无参")
//    @Secured("ROLE_ADMIN")
    @GetMapping("/getUser")
    public Result<User> findByPhone() {
        String phone=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info("用户{}调用了查询接口",phone);
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,phone);
        User user = userService.getOne(lambdaQueryWrapper);
        if (user != null) {
            user.setPassword("");
            user.setPhone(ConcealDataUtils.concealPhone(user.getPhone()));
            return Result.ok("查询成功", user);
        }
        return Result.error("查询失败");
    }
    @ApiOperation("根据id更新用户信息")
    @PutMapping
    public Result<String> updateById(@ApiParam("用户信息") @RequestBody User user) {
        log.info("user:{}", user);
        if (user.getId() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (userService.updateById(user)) {
                return Result.ok("修改成功");
            }
        }
        return Result.error("修改失败");
    }
    @ApiOperation("根据id删除用户  该接口只有ROLE_ADMIN权限才可访问")
    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public Result<String> delete(@ApiParam("用户ID") @NotNull(message = "id不能为空") Long id) {
        if (!userService.removeById(id)) {
            return Result.error("删除成功");
        }
        return Result.ok("删除成功");
    }
}
