package com.xiaoming.rabbit_course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.UserCourse;

import java.util.List;

public interface UserCourseService extends IService<UserCourse> {
    Result<String> insert(UserCourse userCourse);

    Result<String> delete(Long courseId);

    Result<List<Course>> findByUserId();
    public Result<String> collect (Long credentials, UserCourse userCourse);
}
