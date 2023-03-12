package com.xiaoming.rabbit_course.globalException;

import com.xiaoming.rabbit_course.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
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

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> exceptionHandler(AccessDeniedException ex){
        log.info(ex.getMessage());
        return Result.error("无访问权限");
    }

    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException ex){
        log.info(ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler(ValidationException.class)
    public Result<String> exceptionHandler(ValidationException ex){
        log.info(ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> exceptionHandler(BindException ex){
        log.info(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return Result.error(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<String> exceptionHandler(RuntimeException ex){
        log.info(ex.getMessage());
        ex.printStackTrace();
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> exceptionHandler(MethodArgumentTypeMismatchException ex){
        log.info(ex.getMessage());
//        ex.printStackTrace();
        return Result.error("参数非法");
    }
}
