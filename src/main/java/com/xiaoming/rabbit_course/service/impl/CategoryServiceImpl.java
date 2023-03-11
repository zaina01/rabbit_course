package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.mapper.CategoryMapper;
import com.xiaoming.rabbit_course.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
