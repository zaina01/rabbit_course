package com.xiaoming.rabbit_course.Dto;

import com.xiaoming.rabbit_course.entity.Category;
import com.xiaoming.rabbit_course.entity.Course;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class CategoryDto extends Category {
    private List<Course> courses;
    private LocalDate createDate;
    private Integer courseSize;
}
