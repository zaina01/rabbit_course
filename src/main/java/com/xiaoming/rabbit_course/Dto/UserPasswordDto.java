package com.xiaoming.rabbit_course.Dto;

import com.xiaoming.rabbit_course.config.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author admin
 * @Description TODO
 * @Date 2023/4/11 11:27
 * @Version 1.0
 */
@ApiModel(value = "修改密码dto")
@Data
public class UserPasswordDto {
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空",groups = ValidationGroups.Update.class)
    private Long id;
    @ApiModelProperty("用户新密码")
    @NotBlank(message = "新密码不能为空",groups = ValidationGroups.Update.class)
    @Length(min = 6,max = 20,message = "新密码长度应该在6-20之间",groups = ValidationGroups.Update.class)
    private String password;
}
