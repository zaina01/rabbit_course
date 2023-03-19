package com.xiaoming.rabbit_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoming.rabbit_course.Dto.CategoryDto;
import com.xiaoming.rabbit_course.common.Result;
import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.globalException.CustomException;
import com.xiaoming.rabbit_course.mapper.CategoryMapper;
import com.xiaoming.rabbit_course.service.CategoryService;
import com.xiaoming.rabbit_course.service.CourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
@Transactional(rollbackFor = Exception.class)
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private CourseService courseService;

    /**
     * 根据分类id查询分类并查询所关联的课程
     *
     * @param id
     * @return
     */
    @Override
    public Result<Category> findById(Long id) {
//       根据id查询数据库
        Category category = this.getById(id);
        if (category==null){
            return Result.error("分类不存在");
        }
        //查询分类下的课程
        LambdaQueryWrapper<Course> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getCategoryId, category.getId())
                //状态为上架
                .eq(Course::getStatus,0);
        List<Course> courses = courseService.list(lambdaQueryWrapper);
        CategoryDto categoryDto = new CategoryDto();
        //拷贝category 到categoryDto
        BeanUtils.copyProperties(category, categoryDto);
        categoryDto.setCourses(courses);
        return Result.ok("查询成功", categoryDto);

    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public Result<String> delete(Long id) {
        //查询分类下是否关联的有课程
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getCategoryId, id);
        int count = courseService.count(lambdaQueryWrapper);
        //有课程不允许删除
        if (count > 0) {
            throw new CustomException("当前分类下关联的有课程，不能删除");
        }
//        分类下没有关联课程可以删除
        if (this.removeById(id)) {
            return Result.ok("删除分类成功");
        }
        return Result.error("删除分类失败");
    }

    /**
     * 分页查询分类
     *
     * @param page 页码
     * @param size 每页显示数量
     * @return categoryDtoPage
     */
    @Override
    public Result<Page> findAll(Integer page, Integer size, String name) {
        Page<Category> categoryPage = new Page<>(page, size);
        Page<CategoryDto> categoryDtoPage = new Page<>();
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加模糊查询
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), Category::getName, name);
        //查询时按照Sort降序，更新时间降序
        lambdaQueryWrapper.orderByDesc(Category::getSort).orderByDesc(Category::getUpdateTime);
        //分页查询分类
        this.page(categoryPage, lambdaQueryWrapper);
        //拷贝Page
        BeanUtils.copyProperties(categoryPage, categoryDtoPage, "records");
        List<CategoryDto> records = categoryPage.getRecords().stream().map((item) -> {
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(item, categoryDto);
            //查询分类下课程数量
            LambdaQueryWrapper<Course> QueryWrapper = new LambdaQueryWrapper<>();
            //根据分类id查询
            QueryWrapper.eq(Course::getCategoryId, item.getId());
            //统计数量
            int count = courseService.count(QueryWrapper);
            categoryDto.setCourseSize(count);
            //创建日期时间 改成创建日期
            categoryDto.setCreateDate(item.getCreateTime().toLocalDate());
            return categoryDto;
        }).collect(Collectors.toList());
        //添加分页数据
        categoryDtoPage.setRecords(records);
        return Result.ok("查询成功", categoryDtoPage);
    }

    @Override
    public Result<String> updateCategory(Category category) {
        //更新数据库
        if (!this.updateById(category)){
            return Result.error("更新失败");
        }
        return Result.ok("更新成功");
    }
}
