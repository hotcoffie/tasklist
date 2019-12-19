package com.xattit.tasklist.module.security.controller;

import com.xattit.tasklist.module.security.entity.User;
import com.xattit.tasklist.module.security.service.UserService;
import com.xattit.tasklist.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/19 18:57
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("login")
    public Result login(String username, String password) {
        return Result.ok();
    }

    /**
     * 该方法是注册用户的方法，默认放开访问控制
     *
     * @param user
     */
    @PostMapping("signUp")
    public Result signUp(@RequestBody User user) {
        userService.add(user);
        return Result.ok();
    }
}
