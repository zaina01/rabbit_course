package com.xiaoming.rabbit_course.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xiaoming.rabbit_course.config.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@ApiModel("用户类")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("用户id")
    @NotNull(message = "更新信息用户id不能为空",groups = ValidationGroups.Update.class)
    private Long id;

    //    昵称
    @ApiModelProperty("用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    //    用户账号
    @ApiModelProperty("用户账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空",groups = ValidationGroups.Insert.class)
    @Length(min = 6,max = 20,message = "密码长度应该在6-20之间",groups = ValidationGroups.Insert.class)
    @TableField(select = false)
    private String password;

    //    性别 1:男性, 2: 女性
    @ApiModelProperty("性别1:男性, 2: 女性")
    private Integer sex;

    //    头像
    @ApiModelProperty("性别1:男性, 2: 女性")
    private String avatar;
    @ApiModelProperty("状态 1:禁用，0:正常")
    //    状态 1:禁用，0:正常
    private Integer status;
//    角色
    @ApiModelProperty("权限")
    private String role;

    //    创建时间
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    更新时间
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
