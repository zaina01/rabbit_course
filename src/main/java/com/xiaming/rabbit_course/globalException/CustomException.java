package com.xiaming.rabbit_course.globalException;

/**
 * 业务异常封装
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
