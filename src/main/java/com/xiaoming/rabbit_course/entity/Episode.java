package com.xiaoming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xiaoming.rabbit_course.config.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@ApiModel("课程章节")
@Data
public class Episode implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程章节id")
    @NotNull(message = "更新课程章节id不能为空",groups = ValidationGroups.Update.class)
    private Long id;

    //    名称
    @ApiModelProperty("课程章节名称 比如第一集，")
    @NotBlank(message = "name不能为空")
    private String name;

    //    课程id
    @ApiModelProperty("所属课程id")
    @NotNull(message = "所属课程id不能为空")
    private Long courseId;
    //    视频地址
    @ApiModelProperty("视频地址")
    private String video;
    //    排序
    @ApiModelProperty("排序 值越大越靠前")
    private Integer sort;

    //    创建时间
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    更新时间
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
