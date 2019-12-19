package com.xattit.tasklist.module.task.controller;

import com.xattit.tasklist.module.task.entity.Task;
import com.xattit.tasklist.module.task.service.TaskService;
import com.xattit.tasklist.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 13:35
 */
@RestController
@RequestMapping("task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @GetMapping
    public Result search(Integer userId, Date date) {
        List<Map<String, Object>> tasks = taskService.search(userId, date);
        return Result.success(tasks);
    }

    @PostMapping
//    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public Result add(Task task) {
        taskService.add(task);
        return Result.ok();
    }

    @PutMapping
//    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public Result modify(Task task) {
        taskService.modify(task);
        return Result.ok();
    }

    @PostMapping("check")
//    @Secured(value = {"ROLE_ADMIN"})
    public Result checkTasks(Date date) {
        taskService.checkTasks(date);
        return Result.ok();
    }
}
