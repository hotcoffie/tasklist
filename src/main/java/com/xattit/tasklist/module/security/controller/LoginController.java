package com.xattit.tasklist.module.security.controller;

import com.xattit.tasklist.module.security.entity.User;
import com.xattit.tasklist.module.security.service.UserService;
import com.xattit.tasklist.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description 登录监控
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/19 18:57
 */
@RestController
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/login/{status}")
    public String login(@PathVariable String status) {
        if("auth".equals(status)){
            return "没有登录";
        }
        if("fail".equals(status)){
            return "登录失败";
        }
        if("success".equals(status)){
            return "登录成功";
        }
        if("logout".equals(status)){
            return "注销成功";
        }
        return "";
    }

    /**
     * 该方法是注册用户的方法，默认放开访问控制
     *
     * @param user
     */
    @PostMapping("registry")
    public Result signUp(@RequestBody User user) {
        userService.add(user);
        return Result.ok();
    }
}
