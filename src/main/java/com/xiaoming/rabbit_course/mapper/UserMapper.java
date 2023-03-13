package com.xiaoming.rabbit_course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoming.rabbit_course.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select id,password,role,status from user where username=#{username}")
    User selectLogin(String username);
}
