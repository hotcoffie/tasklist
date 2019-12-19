package com.xattit.tasklist.module.security.service;

import com.xattit.tasklist.exception.ApplicationException;
import com.xattit.tasklist.module.security.dao.UserDao;
import com.xattit.tasklist.module.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Resource
    private PasswordEncoder passwordEncoder;

    public void add(User user) {
        if (user == null) {
            throw new ApplicationException("无效的参数");
        }
        List<String> errors = new ArrayList<>();
        String pwd = user.getPassword();
        if (StringUtils.isBlank(pwd) || pwd.length() > 20 || pwd.length() < 6) {
            errors.add("密码必须大于等于6位，小于等于20位");
        }
        String username = user.getUsername();
        if (StringUtils.isBlank(username) || username.length() > 20 || username.length() < 3) {
            errors.add("账号必须大于等于3位，小于等于20位");
        }
        String name = user.getName();
        if (StringUtils.isBlank(name)) {
            errors.add("用户名不能为空");
        } else if (name.length() > 20 || name.length() < 2) {
            errors.add("用户名必须大于等于2位，小于等于20位");
        }
        if (!errors.isEmpty()) {
            throw new ApplicationException(String.join(",", errors));
        }
        User testUser = userDao.selectByUsername(username);
        if (testUser != null) {
            throw new ApplicationException("账号已注册，忘记密码请联系管理员");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(null);
        user.setUpdateTime(null);
        userDao.insertSelective(user);
    }

}
