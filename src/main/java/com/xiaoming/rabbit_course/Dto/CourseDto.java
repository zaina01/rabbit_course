package com.xiaoming.rabbit_course.Dto;

import com.xiaoming.rabbit_course.entity.Course;
import com.xiaoming.rabbit_course.entity.Episode;
import lombok.Data;

import java.util.List;

@Data
public class CourseDto extends Course {
    private String categoryName;
    private List<Episode> episodes;
    private Integer episodeSize;
}
