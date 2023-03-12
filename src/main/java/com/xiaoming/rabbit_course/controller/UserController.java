package com.xiaoming.rabbit_course.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.User;
import com.xiaoming.rabbit_course.service.UserService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Resource
    private UserService userService;
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result<Map<String,String>> login(@ApiParam("账号和密码") @RequestBody User user){
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
    public Result<String> signIn(@ApiParam("注册信息 用户邮箱 账号 密码不能为空 密码长度应该在6-20之间") @Validated @RequestBody User user) {
        log.info("user:{}", user);
        return userService.signIn(user);
    }
    @ApiOperation("根据id查询用户信息 该接口只有ROLE_ADMIN权限才可访问")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public Result<User> findById(@ApiParam("用户ID") @NotNull(message = "id不能为空") @PathVariable Long id) {
        log.info("id:{}", id);
//        log.info("用户{}调用了查询接口", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return userService.findById(id);
    }
    @ApiOperation("用户查询个人信息 该接口无参")
//    @Secured("ROLE_ADMIN")
    @GetMapping
    public Result<User> findByusername() {
        String username=SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info("用户{}调用了查询接口",username);
        return userService.findByusername(username);
    }
    @ApiOperation("根据id更新用户信息")
    @PutMapping
    public Result<String> updateById(@ApiParam("用户信息") @RequestBody User user) {
        log.info("user:{}", user);
        return userService.serviceUpdateById(user);
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
    @ApiOperation("分页查询用户信息，该接口只有ROLE_ADMIN权限才可访问")
    @Secured("ROLE_ADMIN")
    @GetMapping("/{page}/{size}/{username}")
    public Result<Page> page(@ApiParam("页码") @PathVariable int page,@ApiParam("每页显示数") @PathVariable int size,@ApiParam("查询条件手机号，可传可不传") @PathVariable String username){
        return userService.findAll(page,size,username);
    }
}
