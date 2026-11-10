package com.itheima.exception;

import com.itheima.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//表名要返回json的数据类型并且返回异常信息
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//指定异常的类型
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("操作失败");
    }
}
