package com.xiaming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaming.rabbit_course.entity.User;
import com.xiaming.rabbit_course.mapper.UserMapper;
import com.xiaming.rabbit_course.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
