package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.Dto.CategoryDto;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.mapper.CategoryMapper;
import com.xiaoming.rabbit_course.service.CategoryService;
import com.xiaoming.rabbit_course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private CourseService courseService;

    @Override
    public Result<Category> findById(Long id) {
        try {
            Category category = getById(id);
            List<Course> courses = courseService.list(new LambdaQueryWrapper<Course>().eq(Course::getCategoryId, category.getId()));
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            categoryDto.setCourses(courses);
            return Result.ok("查询成功", categoryDto);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public Result<String> delete(Long id) {
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getCategoryId, id);
        int count = courseService.count(lambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("当前分类下关联的有课程，不能删除");
        }
        if (removeById(id)) {
            return Result.ok("删除分类成功");
        }
        return Result.error("删除分类失败");
    }

    @Override
    public Result<Page> findAll(Integer page,Integer size) {
        Page<Category> categoryPage = new Page<>(page, size);
        Page<CategoryDto> categoryDtoPage= new Page<>();
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Category::getSort).orderByDesc(Category::getUpdateTime);
        page(categoryPage, lambdaQueryWrapper);
        BeanUtils.copyProperties(categoryPage,categoryDtoPage,"records");
        List<Category> records = categoryPage.getRecords().stream().map((item)->{
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(item,categoryDto);
            LambdaQueryWrapper<Course> QueryWrapper=new LambdaQueryWrapper<>();
            QueryWrapper.eq(Course::getCategoryId,item.getId());
            int count = courseService.count(QueryWrapper);
            categoryDto.setSize(count);
            categoryDto.setCreateDate(item.getCreateTime().toLocalDate());
            return categoryDto;
        }).collect(Collectors.toList());

        return Result.ok("查询成功",categoryDtoPage);
    }
}
