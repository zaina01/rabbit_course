package com.xiaoming.rabbit_course.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @ApiOperation("新增课程")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public Result<String> save(@ApiParam("课程信息") @RequestBody Course course){
        if (courseService.save(course)){
            return Result.ok("添加课程成功");
        }
        return Result.error("添加课程失败");
    }
//    @Secured("ROLE_ADMIN")
//    @ApiOperation("删除课程")
//    @DeleteMapping("/delete")
//    public Result<String> delete(@ApiParam("课程id") @NotNull(message = "id不能为空") Long id){
//        return courseService.delete(id);
//    }
    @ApiOperation("修改课程信息")
    @Secured("ROLE_ADMIN")
    @PutMapping
    public Result<String> update(@ApiParam("要修改的课程信息") @RequestBody Course course){
        if (courseService.updateById(course)){
            return Result.ok("修改课程成功");
        }
        return Result.error("修改课程失败");
    }
//    @ApiOperation("/查询课程信息")
//    @GetMapping("/findById")
//    public Result<Course> findById(@ApiParam("课程ID") @NotNull(message = "id不能为空") Long id){
//        return courseService.findById(id);
//    }

}
