package com.xattit.tasklist.conf;

import com.xattit.tasklist.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void runtimeExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e) {
        log.error("系统异常", e);
        return Result.fail("系统异常，稍后再试");
    }
}
