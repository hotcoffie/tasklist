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
 * Description: 全局请求日志
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
     * 打印请求日志
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
            log.error("路径: {} {}", request.getMethod(), request.getRequestURI());
            log.error("参数: {}", Arrays.toString(point.getArgs()));
            log.error("耗时: {} ms", System.currentTimeMillis() - startTime);
            throw throwable;
        }
        log.info("路径: {} {}", request.getMethod(), request.getRequestURI());
        log.info("参数: {}", Arrays.toString(point.getArgs()));
        log.info("返回: {}", JSON.toJSONString(result));
        log.info("耗时: {} ms", System.currentTimeMillis() - startTime);
        return result;
    }
}
