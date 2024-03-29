package com.xiaoming.rabbit_course.controller;

import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.UserCourse;
import com.xiaoming.rabbit_course.service.UserCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
@Validated
@RestController
@RequestMapping("/userCourse")
public class UserCourseController {
    @Resource
    private UserCourseService userCourseService;
    @ApiOperation("收藏")
    @PostMapping(consumes = "application/json",produces = "application/json")
    public Result<String> save(@RequestBody @Validated UserCourse userCourse){
        return userCourseService.insert(userCourse);
    }


    @ApiOperation("取消收藏")
    @DeleteMapping("/{courseId}")
    public Result<String> delete(@ApiParam(value = "课程Id",example ="0") @PathVariable @NotNull(message = "id不能为空") Long courseId){
        return userCourseService.delete(courseId);
    }

    @ApiOperation("查看收藏")
    @GetMapping
    public Result<List<Course>> findByUserId(){
        return userCourseService.findByUserId();
    }
}
