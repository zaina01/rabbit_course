package com.xiaoming.rabbit_course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.UserCourse;

public interface UserCourseService extends IService<UserCourse> {
    Result<String> insert(UserCourse userCourse);

    Result<String> delete(Long courseId);
}
