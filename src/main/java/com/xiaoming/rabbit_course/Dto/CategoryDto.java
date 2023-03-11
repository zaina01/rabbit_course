package com.xiaoming.rabbit_course.Dto;

import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.entity.Course;
import lombok.Data;

import java.util.List;
@Data
public class CategoryDto extends Category {
    private List<Course> courses;
}
