package com.xiaoming.rabbit_course.globalException;

import com.xiaoming.rabbit_course.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //访问异常
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> exceptionHandler(AccessDeniedException ex){
        log.info(ex.getMessage());
        return Result.error("无访问权限");
    }
    //认证异常
    @ExceptionHandler(BadCredentialsException.class)
    public Result<String> exceptionHandler(BadCredentialsException ex){
        log.info(ex.getMessage());
        return Result.error("用户名或密码错误");
    }
    //认证异常
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result<String> exceptionHandler(InternalAuthenticationServiceException ex){
        log.info(ex.getMessage());
        return Result.error("用户名或密码错误");
    }
    //业务异常
    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException ex){
        log.info(ex.getMessage());
        return Result.error(ex.getMessage());
    }
    //数据校验异常
    @ExceptionHandler(ValidationException.class)
    public Result<String> exceptionHandler(ValidationException ex){
        log.info(ex.getMessage());
        return Result.error(ex.getMessage());
    }
    //数据校验异常
    @ExceptionHandler(BindException.class)
    public Result<String> exceptionHandler(BindException ex){
        log.info(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return Result.error(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
    //运行异常
    @ExceptionHandler(RuntimeException.class)
    public Result<String> exceptionHandler(RuntimeException ex){
        log.info(ex.getMessage());
        ex.printStackTrace();
        return Result.error("网络繁忙");
    }
    //请求参数异常
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> exceptionHandler(MethodArgumentTypeMismatchException ex){
        log.info(ex.getMessage());
//        ex.printStackTrace();
        return Result.error("参数非法");
    }

}
