package com.xiaming.rabbit_course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //    昵称
    private String name;

    //    手机号
    private String phone;

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
