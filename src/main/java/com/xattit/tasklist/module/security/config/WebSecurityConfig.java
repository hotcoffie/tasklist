package com.xattit.tasklist.module.security.config;

import com.alibaba.fastjson.JSONObject;
import com.xattit.tasklist.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/19 20:26
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置通用权限限制，任意请求都需要登录
        // http.authorizeRequests().anyRequest().authenticated();

        // 处理无权限异常，因为配置了springboot的全局异常捕获，会先一步catch到异常，这里无须再配置
        /*http.exceptionHandling()
                .accessDeniedHandler((req, resp, e) -> {
                    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    log.error("非法请求 Path: {} {}\tIP: {}\tUserID: {}",
                            req.getMethod(), req.getRequestURI(), IpTool.getAddr(req), user.getId());
                    jsonResult(resp, Result.success("无访问权限"));
                })*/

        // 配置登录相关
        http.formLogin()
                // 自定义登入成功处理
                .successHandler((req, resp, auth) -> {
                    log.info("登录成功: {}", JSONObject.toJSONString(auth.getPrincipal()));
                    jsonResult(resp, Result.success(auth.getPrincipal()));
                })
                // 自定义登录失败处理
                // .failureHandler((req, resp, e) -> {
                //     log.error("认证失败: {} ", e.getMessage());
                //     jsonResult(resp, Result.fail("认证失败"));
                // })
                .and().rememberMe().tokenValiditySeconds(1209600);

        // 配置注销相关
        http.logout()
                //自定义注销成功处理
                .logoutSuccessHandler((req, resp, auth) -> jsonResult(resp, Result.ok()));
        // 关闭csrf
        http.csrf().disable();

        // 防止同账号多处登录
        http.sessionManagement().maximumSessions(1);
    }

    /**
     * 适配全局的json返回格式
     */
    private void jsonResult(HttpServletResponse resp, Result result) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
