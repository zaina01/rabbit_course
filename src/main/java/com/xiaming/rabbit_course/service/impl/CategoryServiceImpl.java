package com.xiaming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaming.rabbit_course.entity.Category;
import com.xiaming.rabbit_course.mapper.CategoryMapper;
import com.xiaming.rabbit_course.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
