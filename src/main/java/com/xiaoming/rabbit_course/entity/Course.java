package com.xiaoming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@ApiModel("课程")
@Data
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("课程id")
    private Long id;

    //    课程分类id
    @ApiModelProperty("课程所属分类id")
    private Long categoryId;

    //    课程名称
    @ApiModelProperty("课程名称")
    private String name;

    //    课程排序
    @ApiModelProperty("课程排序 值越大越靠前")
    private Integer sort;

    //    课程图标
    @ApiModelProperty("课程图标")
    private String avatar;

    //    课程状态
    @ApiModelProperty("课程状态 0上架，1下架")
    private Integer status;

    //    课程介绍
    @ApiModelProperty("课程介绍")
    private String description;

    //    创建时间
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    更新时间
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
