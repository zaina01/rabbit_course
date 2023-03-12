package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.entity.UserCourse;
import com.xiaoming.rabbit_course.mapper.UserCourseMapper;
import com.xiaoming.rabbit_course.service.UserCourseService;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {
}
