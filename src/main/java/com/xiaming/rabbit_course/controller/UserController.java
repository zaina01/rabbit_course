package com.xiaming.rabbit_course.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaming.rabbit_course.common.Result;
import com.xiaming.rabbit_course.entity.User;
import com.xiaming.rabbit_course.service.UserService;
import com.xiaming.rabbit_course.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;









//    @PostMapping ("/sendMsg")
//    public Result<String> sendMsg(@RequestBody String phone){
//
//        if(StringUtils.isNotEmpty(phone)){
//            String code = ValidateCodeUtils.getValidateCode().toString();
//            log.info("验证码为：{}",code);
////            发送验证码
//
//            //存放到redis 设置有效期
//            redisTemplate.opsForValue().set("user:code:"+phone,code,5, TimeUnit.MINUTES);
//            return Result.ok("成功");
//        }
//        return Result.error(10001,"失败");
//    }
//    @PostMapping("/login")
//    public Result<User> login(@RequestBody Map map){
//        log.info("map:{}",map);
//        String phone = (String) map.get("phone");
//        String code= (String) map.get("code");
//        //从redis中获取缓存的验证码
//        String rediscode = (String) redisTemplate.opsForValue().get("user:code:" + phone);
//        if (rediscode!=null&&rediscode.equals(code)){
//            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(User::getPhone,phone);
//            User user = userService.getOne(lambdaQueryWrapper);
//            if (user==null){
//                //新用户
//                user = new User();
//                user.setPhone(phone);
//                user.setAvatar("100000000000000001.jpg");
//                user.setStatus(1);
//                userService.save(user);
//            }
//            redisTemplate.opsForValue().set("user:token"+phone,user.getId());
//            return Result.ok("登录成功",user);
//        }
//        return Result.error(100002,"登录失败");
//    }
}
