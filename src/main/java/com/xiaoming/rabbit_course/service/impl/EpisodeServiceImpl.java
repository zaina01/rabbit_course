package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.Episode;
import com.xiaoming.rabbit_course.mapper.EpisodeMapper;
import com.xiaoming.rabbit_course.service.CourseService;
import com.xiaoming.rabbit_course.service.EpisodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class EpisodeServiceImpl extends ServiceImpl<EpisodeMapper, Episode> implements EpisodeService {
    @Resource
    private CourseService courseService;
    @Override
    public boolean delete(Long id) {
        //查询章节信息
        Episode episode = this.getById(id);
        //查询要删除的章节对应的课程下是否还关联的有章节
        int count = this.count(new LambdaQueryWrapper<Episode>().eq(Episode::getCourseId, episode.getCourseId()));
        if (count==0){
            //要删除的章节对应的课程下没有关联章节 修改为下架状态
            Course course = new Course();
            course.setStatus(1);
            course.setId(episode.getCourseId());
            courseService.updateById(course);
        }
        return this.removeById(id);
    }
}
