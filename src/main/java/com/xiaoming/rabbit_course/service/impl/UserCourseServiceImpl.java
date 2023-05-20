package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.UserCourse;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.mapper.UserCourseMapper;
import com.xiaoming.rabbit_course.service.CourseService;
import com.xiaoming.rabbit_course.service.UserCourseService;
import org.springframework.aop.framework.AopContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {
    @Resource
    private CourseService courseService;
    /**
     * 收藏课程
     * @param userCourse 收藏信息
     * @return 收藏结果
     */
//    @Override
//    public Result<String> insert(UserCourse userCourse) {
//        Long credentials = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
//        synchronized (credentials.toString().intern()) {  //.intern()返回字符串规范对象，不会直接new 新字符串对象，先去常量池找
//            return this.collect(credentials,userCourse);
//        }
//    }
    @Override
    public Result<String> collect (UserCourse userCourse){
        //查询用户是否已收藏过该课程
        LambdaQueryWrapper<UserCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserCourse::getCourseId, userCourse.getCourseId());
        lambdaQueryWrapper.eq(UserCourse::getUserId, userCourse.getUserId());
        UserCourse one = this.getOne(lambdaQueryWrapper);
        //用户已收藏过该课程
        if (one != null) {
            throw new CustomException("不允许重复收藏");
        }
        //用户未收藏过该课程，开始保存
        if (save(userCourse)) {
            return Result.ok("收藏成功");
        }
        return Result.error("收藏失败");
    }

    @Override
    public Result<String> findByUserIdAndCourseId(Long courseId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        LambdaQueryWrapper<UserCourse> lambdaQueryWrapper=  new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserCourse::getCourseId,courseId).eq(UserCourse::getUserId,userId);
        UserCourse userCourse = this.getOne(lambdaQueryWrapper);
        if (userCourse!=null){
            return Result.ok("已收藏");
        }
        return Result.error("未收藏");
    }

    @Override
    public Result<String> insert(Long courseId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        UserCourse userCourse = new UserCourse();
        userCourse.setCourseId(courseId);
        userCourse.setUserId(userId);
        synchronized (userId.toString().intern()) {  //.intern()返回字符串规范对象，不会直接new 新字符串对象，先去常量池找
            return this.collect(userCourse);
        }
    }

    /**
     * 取消收藏课程
     * @param courseId 收藏课程的id
     * @return 结果
     */
    @Override
    public Result<String> delete(Long courseId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        LambdaQueryWrapper<UserCourse> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserCourse::getUserId,userId).eq(UserCourse::getCourseId,courseId);
        if (this.remove(lambdaQueryWrapper)){
            return Result.ok("取消收藏成功");
        }
        return Result.error("取消收藏失败");
    }

    @Override
    public Result<List<Course>> findByUserId() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        //根据用户id查询收藏的课程
        LambdaQueryWrapper<UserCourse> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserCourse::getUserId,userId);
        lambdaQueryWrapper.select(UserCourse::getCourseId);
        //查询收藏的课程集合
        List<UserCourse> list = this.list(lambdaQueryWrapper);
        if(!(list.size() >0)){
            return Result.error("没有收藏的课程");
        }
        List<Long> courseIds = list.stream().map(UserCourse::getCourseId).collect(Collectors.toList());
        LambdaQueryWrapper<Course> courseLambdaQueryWrapper=new LambdaQueryWrapper<>();
        courseLambdaQueryWrapper.in(Course::getId, courseIds);
        List<Course> courseList = courseService.list(courseLambdaQueryWrapper);
        return Result.ok("查询成功",courseList);
    }

}
