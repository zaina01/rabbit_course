package com.xiaoming.rabbit_course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;

public interface CourseService extends IService<Course> {
    Result<Course> findById(Long id);
}
