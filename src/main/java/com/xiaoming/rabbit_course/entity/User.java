package com.xiaoming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@ApiModel("用户类")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //    昵称
    @ApiModelProperty("用户昵称")
    @NotBlank(message = "昵称不能为空")
    private String name;

    //    手机号
    @ApiModelProperty("用户手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 5,max = 20,message = "密码长度应该在6-20之间")
    private String password;

    //    性别 1:男性, 2: 女性
    private Integer sex;

    //    头像
    private String avatar;

    //    状态 0:禁用，1:正常
    private Integer status;

    //    创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //    更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
