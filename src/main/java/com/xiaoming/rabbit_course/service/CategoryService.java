package com.xiaoming.rabbit_course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Category;

public interface CategoryService extends IService<Category> {
    Result<Category> findById(Long id);

    Result<String> delete(Long id);

    Result<Page> findAll(Integer page,Integer size,String name);

    Result<String> updateCategory(Category category);
}
