package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.UserCourse;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.mapper.UserCourseMapper;
import com.xiaoming.rabbit_course.service.UserCourseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {

    @Override
    public Result<String> insert(UserCourse userCourse) {
//        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        synchronized (principal) {
            //查询用户是否已选过该课程
            LambdaQueryWrapper<UserCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserCourse::getCourseId, userCourse.getCourseId());
            lambdaQueryWrapper.eq(UserCourse::getUserId, userCourse.getUserId());
            UserCourse one = getOne(lambdaQueryWrapper);
            //用户已选过该课程
            if (one != null) {
                throw new CustomException("不允许重复选课");
            }
            //用户未选过该课程，开始保存
            if (save(userCourse)) {
                return Result.ok("选课成功");
            }
//        }
        return Result.error("选课失败");
    }

    @Override
    public Result<String> delete(Long courseId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        LambdaQueryWrapper<UserCourse> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserCourse::getUserId,userId).eq(UserCourse::getCourseId,courseId);
        if (remove(lambdaQueryWrapper)){
            return Result.ok("退课成功");
        }
        return Result.error("退课失败");
    }
}
