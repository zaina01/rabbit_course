package com.xiaming.rabbit_course.controller;



import com.xiaming.rabbit_course.common.Result;
import com.xiaming.rabbit_course.entity.User;
import com.xiaming.rabbit_course.service.UserService;
import com.xiaming.rabbit_course.utils.ConcealDataUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping
    public Result<String> signIn(@Validated @RequestBody User user) {
        log.info("user:{}", user);
        userService.save(user);
        return Result.ok("注册成功");
    }

    @GetMapping
    public Result<User> findById(@NotNull(message = "id不能为空") Long id) {
        log.info("id:{}", id);
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword("");
            user.setPhone(ConcealDataUtils.concealPhone(user.getPhone()));
            return Result.ok("查询成功", user);
        }
        return Result.error(10001, "查询失败");
    }

    @PutMapping
    public Result<String> updateById(@Validated @RequestBody User user) {
        log.info("user:{}", user);
        if (user.getId()!=null){
            if (userService.updateById(user)){
                return Result.ok("修改成功");
            }
        }
        return Result.error(10001,"修改成功");
    }

    @DeleteMapping
    public Result<String> delete(@NotNull(message = "id不能为空") Long id) {
        if (!userService.removeById(id)) {
            return Result.error(10001,"删除成功");
        }
        return Result.ok("删除成功");
    }


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
