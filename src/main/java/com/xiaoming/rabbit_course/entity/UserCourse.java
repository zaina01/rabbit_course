package com.xiaoming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
@ApiModel("用户收藏")
@Data
public class UserCourse implements Serializable {
    private static final long serialVersionUID=1L;
    @ApiModelProperty("主键id")
    private Long id;

    //用户id
    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
    private Long userId;
    //课程id
    @ApiModelProperty("课程id")
    @NotBlank(message = "课程id不能为空")
    private Long courseId;

    //创建时间
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
