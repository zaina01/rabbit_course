package com.xiaoming.rabbit_course.controller;

import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.UserCourse;
import com.xiaoming.rabbit_course.service.UserCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userCourse")
public class UserCourseController {
    @Resource
    private UserCourseService userCourseService;
    @ApiOperation("选课")
    @PostMapping
    public Result<String> save(@RequestBody UserCourse userCourse){
        return userCourseService.insert(userCourse);
    }
    @ApiOperation("退课")
    @DeleteMapping("/{courseId}")
    public Result<String> delete(@ApiParam(value = "课程Id") @PathVariable Long courseId){
        return userCourseService.delete(courseId);
    }
}
