package com.xiaoming.rabbit_course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoming.rabbit_course.Dto.UserPasswordDto;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Result<Map<String,String>> login(User user);

    Result<String> logout();

    Result<String> signIn(User user);

    Result<String> serviceUpdateById(User user);

    Result<User> findByusername(String username);

    Result<User> findById(Long id);

    Result<Page> findAll(int page, int size,String username);

    boolean usernameExists(String username);
    Result<String> EditPassword(String username,UserPasswordDto userPasswordDto);
    Result<String> signInUser(User user);
}
