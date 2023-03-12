package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.Dto.CourseDto;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.Episode;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.mapper.CourseMapper;
import com.xiaoming.rabbit_course.service.CourseService;
import com.xiaoming.rabbit_course.service.EpisodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>implements CourseService {
    @Resource
    private EpisodeService episodeService;
    @Override
    public Result<Course> findById(Long id) {
        Course course = getById(id);
        LambdaQueryWrapper<Episode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Episode::getCourseId,id);
        List<Episode> episodes = episodeService.list(queryWrapper);
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course,courseDto);
        courseDto.setEpisodes(episodes);
        return Result.ok("查询成功",courseDto);
    }

    @Override
    public Result<Page> findAll(int page, int size, String name) {
        Page<Course> coursePage = new Page<>(page, size);
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name),Course::getName,name);
        Page<Course> courses = page(coursePage, lambdaQueryWrapper);
        return Result.ok("查询成功",courses);
    }

    @Override
    public Result<String> delete(Long id) {
        LambdaQueryWrapper<Episode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Episode::getCourseId,id);
        int count = episodeService.count(queryWrapper);
        if(count>0){
            throw new CustomException("当前课程下关联的有课程视频，无法删除");
        }
        if (removeById(id)){
            return Result.ok("删除成功");
        }
        return Result.ok("删除失败");
    }
}
