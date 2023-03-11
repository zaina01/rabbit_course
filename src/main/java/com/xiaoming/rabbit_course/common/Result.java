package com.xiaoming.rabbit_course.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class Result<T> implements Serializable {
    private Integer code; //状态码

    private String msg; //消息

    private T data;

    public static <T> Result<T> ok(String msg,T data){
        Result<T> r = new Result<>();
        r.code=200;
        r.msg=msg;
        r.data=data;
        return r;
    }
    public static <T> Result<T> ok(String msg){
        Result<T> r = new Result<>();
        r.code=200;
        r.msg=msg;
        return r;
    }
    public static <T> Result<T>error(String msg){
        Result<T> r = new Result<>();
        r.code=0;
        r.msg=msg;
        return r;
    }
}
