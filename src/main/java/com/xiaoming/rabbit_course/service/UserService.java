package com.xiaoming.rabbit_course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    Result<Map<String,String>> login(User user);

    Result<String> logout();
}
