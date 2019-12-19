package com.xattit.tasklist.conf;

import com.xattit.tasklist.exception.ApplicationException;
import com.xattit.tasklist.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 16:15
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @ExceptionHandler(ApplicationException.class)
    public Result runtimeExceptionHandler(ApplicationException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void runtimeExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest req,HttpServletResponse resp) throws IOException {
        log.error("不支持的请求方式\tPath: {}\tMethod: {}",req.getRequestURI(),e.getMethod());
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e) {
        log.error("系统异常", e);
        return Result.fail("系统异常，稍后再试");
    }
}
