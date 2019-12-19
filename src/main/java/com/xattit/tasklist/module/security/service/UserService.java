package com.xattit.tasklist.module.security.service;

import com.xattit.tasklist.module.security.dao.UserDao;
import com.xattit.tasklist.module.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/19 17:22
 */
@Service
@Slf4j
public class UserService {
    @Resource
    private UserDao userDao;
//    @Resource
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void add(User user) {
        if (user == null) {
            throw new RuntimeException("无效的参数");
        }
        List<String> errors = new ArrayList<>();
        String pwd = user.getPassword();
        if (StringUtils.isBlank(pwd)) {
            errors.add("密码不能为空");
        }
        if (pwd.length() > 20 || pwd.length() < 6) {
            errors.add("密码必须大于等于6位，小于等于20位");
        }
        String username = user.getUsername();
        if (StringUtils.isBlank(username)) {
            errors.add("账号不能为空");
        }
        if (username.length() > 20) {
            errors.add("账号不能超过20位");
        }
        String name = user.getName();
        if (StringUtils.isBlank(name)) {
            errors.add("用户名不能为空");
        }
        if (name.length() > 20) {
            errors.add("用户名不能超过20位");
        }
        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join(",", errors));
        }
        User testUser = userDao.selectByUsername(username);
        if (testUser != null) {
            throw new RuntimeException("账号已注册，忘记密码请联系管理员");
        }
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreateTime(null);
        user.setUpdateTime(null);
        userDao.insertSelective(user);
    }

}
