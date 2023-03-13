package com.xiaoming.rabbit_course.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @ApiOperation("新增课程 需要ROLE_ADMIN权限")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public Result<String> save(@ApiParam(value ="课程信息") @RequestBody Course course){
        if (courseService.save(course)){
            return Result.ok("添加课程成功");
        }
        return Result.error("添加课程失败");
    }
    @Secured("ROLE_ADMIN")
    @ApiOperation("删除课程  需要ROLE_ADMIN权限")
    @DeleteMapping("/{id}")
    public Result<String> delete(@ApiParam(value ="课程id",example = "0") @NotNull(message = "id不能为空") @PathVariable Long id){
        return courseService.delete(id);
    }
    @ApiOperation("修改课程信息 需要ROLE_ADMIN权限")
    @Secured("ROLE_ADMIN")
    @PutMapping
    public Result<String> update(@ApiParam(value ="要修改的课程信息") @RequestBody Course course){
        if (courseService.updateById(course)){
            return Result.ok("修改课程成功");
        }
        return Result.error("修改课程失败");
    }
    @ApiOperation("/查询课程信息以及课程下关联的课程片段")
    @GetMapping("/{id}")
    public Result<Course> findById(@ApiParam(value ="课程ID",example = "0") @NotNull(message = "id不能为空") @PathVariable Long id){
        return courseService.findById(id);
    }
    @ApiOperation("/查询全部课程")
    @GetMapping("/{page}/{size}")
    public Result<Page> page(@ApiParam(value ="页码",example = "0") @NotNull(message = "page不能为空") @PathVariable Integer page, @ApiParam(value = "每页显示数",example = "0") @NotNull(message = "size不能为空") @PathVariable Integer size, @ApiParam("查询条件课程名，可传可不传") String name){
        return courseService.findAll(page,size,name);
    }

}
