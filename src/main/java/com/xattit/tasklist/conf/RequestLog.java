package com.xattit.tasklist.conf;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/12/20 上午 12:07
 */
@Aspect
@Component
@Slf4j
public class RequestLog {
    /**
     * 指定切点
     */
    @Pointcut("execution(public * com.*.*.module.*.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     */
    @Around("webLog()")
    public Object logHandler(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object result;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            log.error("\n请求路径: ({}){}\n请求参数: {}\n执行时间: {} ms",
                    request.getMethod(), request.getRequestURI(),
                    Arrays.toString(point.getArgs()),
                    System.currentTimeMillis() - startTime
            );
            throw throwable;
        }
        log.info("\n请求路径: [{}] {}\n请求参数: {}\n  返回值: {}\n执行时间: {} ms",
                request.getMethod(), request.getRequestURI(),
                Arrays.toString(point.getArgs()),
                JSON.toJSONString(result),
                System.currentTimeMillis() - startTime
        );
        return result;
    }
}
