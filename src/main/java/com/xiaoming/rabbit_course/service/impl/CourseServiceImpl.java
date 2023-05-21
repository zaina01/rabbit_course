package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.Dto.CourseDto;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.Episode;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.mapper.CourseMapper;
import com.xiaoming.rabbit_course.service.CategoryService;
import com.xiaoming.rabbit_course.service.CourseService;
import com.xiaoming.rabbit_course.service.EpisodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>implements CourseService {
    @Resource
    private EpisodeService episodeService;
    @Resource
    private CategoryService categoryService;
    /**
     * 查询课程信息，并查询课程下的课程章节
     * @param id 课程id
     * @return 课程信息
     */
    @Override
    public Result<Course> findById(Long id) {
        Course course = this.getById(id);
        if (course==null){
            return Result.error("课程不存在");
        }
        //查询课程所属分类
        Category category = categoryService.getById(course.getCategoryId());
        //构造条件查询课程下的章节
        LambdaQueryWrapper<Episode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Episode::getCourseId,id);
        //课程章节按sort降序排序，再按创建时间升序排序
        queryWrapper.orderByDesc(Episode::getSort).orderByAsc(Episode::getCreateTime);
        //查询课程下的章节
        List<Episode> episodes = episodeService.list(queryWrapper);
        CourseDto courseDto = new CourseDto();
        //拷贝bean
        BeanUtils.copyProperties(course,courseDto);
        courseDto.setCategoryName(category.getName());
        courseDto.setEpisodes(episodes);
        return Result.ok("查询成功",courseDto);
    }

    /**
     * 课程分页查询
     * @param page 页码
     * @param size 数量
     * @param name 查询条件
     * @return 查询结果
     */
    @Override
    public Result<Page> findAll(int page, int size, String name,boolean isAdmin) {
        Page<Course> coursePage = new Page<>(page, size);
        Page<CourseDto> courseDtoPage=new Page<>();
        //模糊查询课程条件
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name),Course::getName,name);
        if (!isAdmin){
            lambdaQueryWrapper.eq(Course::getStatus,0);
        }
        this.page(coursePage, lambdaQueryWrapper);
        //bena拷贝
        BeanUtils.copyProperties(coursePage,courseDtoPage,"records");
        List<CourseDto> courseDtos = coursePage.getRecords().stream().map((item) -> {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(item, courseDto);
            //查询分类名称
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                courseDto.setCategoryName(category.getName());
            }
            //查询课程下资源数量
            LambdaQueryWrapper<Episode> QueryWrapper=new LambdaQueryWrapper<>();
            QueryWrapper.eq(Episode::getCourseId,courseDto.getId());
            int count = episodeService.count(QueryWrapper);
            courseDto.setEpisodeSize(count);
            return courseDto;
        }).collect(Collectors.toList());
        courseDtoPage.setRecords(courseDtos);
        return Result.ok("查询成功",courseDtoPage);
    }

    /**
     * 根据课程id 删除课程
     * @param id 课程id
     * @return 删除结果
     */
    @Override
    public Result<String> delete(Long id) {
        LambdaQueryWrapper<Episode> queryWrapper = new LambdaQueryWrapper<>();
        //先查询是否关联的有课程章节，如果有不允许删除
        queryWrapper.eq(Episode::getCourseId,id);
        int count = episodeService.count(queryWrapper);
        if(count>0){
            throw new CustomException("当前课程下关联的有课程视频，无法删除");
        }
        if (this.removeById(id)){
            return Result.ok("删除成功");
        }
        return Result.ok("删除失败");
    }

    @Override
    public Result<String> updateCourse(Course course) {
        //如果状态不更新为上架状态可直接更新
        if (course.getStatus()!=0){
            this.updateById(course);
            return Result.ok("更新成功");
        }
        LambdaQueryWrapper<Episode> queryWrapper = new LambdaQueryWrapper<>();
        //查询是否关联的有课程章节，如果没有不允许上架
        queryWrapper.eq(Episode::getCourseId,course.getId());
        int count = episodeService.count(queryWrapper);
        if (count<1){
            return  Result.error("该课程还没有章节，无法上架");
        }
        //有关联的章节，可以更新上架
        this.updateById(course);
        return Result.ok("更新成功");
    }
}
