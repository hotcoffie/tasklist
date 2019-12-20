package com.xattit.tasklist.conf;

import com.xattit.tasklist.exception.ApplicationException;
import com.xattit.tasklist.module.security.entity.User;
import com.xattit.tasklist.tool.IpTool;
import com.xattit.tasklist.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void runtimeExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.error("不支持的请求方式\tPath: {}\tMethod: {}", req.getRequestURI(), e.getMethod());
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedExceptionHandler(AccessDeniedException e, HttpServletRequest req, HttpServletResponse resp) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            log.error("非法请求 Path: {} {}\tIP: {}\t",
                    req.getMethod(), req.getRequestURI(), IpTool.getAddr(req));
            return Result.fail("请先登录");
        } else {
            User user = (User) principal;
            log.error("非法请求 Path: {} {}\tIP: {}\tUserID: {}", req.getMethod(), req.getRequestURI(), IpTool.getAddr(req), user.getId());
            return Result.fail("无访问权限");
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationExceptionHandler(AuthenticationException e, HttpServletRequest req) {
        log.error("认证失败 Path: {} {}\tIP: {}\t{}", req.getMethod(), req.getRequestURI(), IpTool.getAddr(req), e.getMessage());
        return Result.fail("认证失败：" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e) {
        log.error("系统异常", e);
        return Result.fail("系统异常，稍后再试");
    }
}
