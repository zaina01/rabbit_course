package com.xiaming.rabbit_course.globalException;

import com.xiaming.rabbit_course.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> exceptionHandler(AccessDeniedException ex){
        log.info(ex.getMessage());
        return Result.error("权限不足");
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
}
