package com.xiaoming.rabbit_course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoming.rabbit_course.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
