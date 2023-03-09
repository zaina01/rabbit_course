package com.xiaming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //    课程分类id
    private Long categoryId;

    //    课程名称
    private String name;

    //    课程排序
    private Integer sort;

    //    课程图标
    private String avatar;

    //    课程状态
    private Integer status;

    //    课程介绍
    private String description;

    //    创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
