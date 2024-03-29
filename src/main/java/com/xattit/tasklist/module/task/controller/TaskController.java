package com.xattit.tasklist.module.task.controller;

import com.xattit.tasklist.module.task.entity.Task;
import com.xattit.tasklist.module.task.service.TaskService;
import com.xattit.tasklist.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 13:35
 */
@RestController
@RequestMapping("task")
@Slf4j
public class TaskController {
    @Resource
    private TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or (hasAuthority('ROLE_USER') and #userId == authentication.principal.id)")
    public Result search(Integer userId, Date date) {
        List<Task> tasks = taskService.search(userId, date);
        return Result.success(tasks);
    }

    @PostMapping
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public Result add(Task task) {
        taskService.add(task);
        return Result.ok();
    }

    @PutMapping
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public Result modify(Task task) {
        taskService.modify(task);
        return Result.ok();
    }

    @PostMapping("check")
    @Secured(value = {"ROLE_ADMIN"})
    public Result checkTasks(Date date) {
        int count = taskService.checkTasks(date);
        return Result.success(count);
    }
}
