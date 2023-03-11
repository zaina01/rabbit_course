package com.xiaoming.rabbit_course.utils;

import java.util.Random;

/**
 * 生产验证码工具
 */
public class ValidateCodeUtils {
    /**
     * 生成6位验证码
     * @return 验证码
     */
    public static Integer getValidateCode(){
        Random random = new Random();
        int code = random.nextInt(999999);
        if (code<100000){
            code+=100000;
        }
        return code;
    }
}
