package com.xiaoming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@ApiModel("课程片段")
@Data
public class Episode implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程片段id")
    private Long id;

    //    名称
    @ApiModelProperty("课程片段名称 比如第一集，")
    private String name;

    //    课程id
    @ApiModelProperty("所属课程id")
    private Long courseId;

    //    排序
    @ApiModelProperty("排序 值越大越靠前")
    private Integer sort;

    //    创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
