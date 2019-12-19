package com.xattit.tasklist.module.task.service;

import com.xattit.tasklist.exception.ApplicationException;
import com.xattit.tasklist.module.security.dao.UserDao;
import com.xattit.tasklist.module.task.dao.TaskDao;
import com.xattit.tasklist.module.task.entity.Task;
import com.xattit.tasklist.tool.DateTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 13:39
 */
@Service
@Transactional
public class TaskService {
    @Resource
    private TaskDao taskDao;
    @Resource
    private UserDao userDao;

    public List<Task> search(Integer userId, Date date) {
        date = DateTool.getMonDayOfWeek(date);
        return taskDao.search(userId, date);
    }

    public void add(Task task) {
        checkContent(task);
        Integer userId = task.getUserId();
        if (userId == null || userId <= 0 || userDao.selectByPrimaryKey(userId) == null) {
            throw new ApplicationException("用户ID无效");
        }
        Date createTime = task.getCreateTime();
        if (createTime == null) {
            throw new ApplicationException("请指定任务所属日期");
        }
        createTime = DateTool.getMonDayOfWeek(createTime);
        List<Task> tasks = taskDao.findByUserIdAndCreateTime(userId, createTime);
        if (tasks != null && !tasks.isEmpty()) {
            throw new ApplicationException("已提交过任务，请使用编辑");
        }
        task.setCreateTime(createTime);
        taskDao.add(task);
    }

    public void modify(Task task) {
        checkContent(task);
        Task oldTask = taskDao.findById(task.getId());
        if (oldTask == null) {
            throw new ApplicationException("无效的任务ID");
        }
        if ('0' != oldTask.getChecked()) {
            throw new ApplicationException("已审核任务不能修改");
        }
        taskDao.modify(task);
    }

    private void checkContent(Task task) {
        if (task == null) {
            throw new ApplicationException("无效的参数");
        }
        if (task.getThisWeek() == null || task.getThisWeek().isBlank()) {
            throw new ApplicationException("本周任务不能为空");
        }
        if (task.getAfterWeek() == null || task.getAfterWeek().isBlank()) {
            throw new ApplicationException("下周任务不能为空");
        }

    }

    public void checkTasks(Date date) {
        if(date==null){
            throw new ApplicationException("请指定任务所属日期");
        }
        date = DateTool.getMonDayOfWeek(date);
        taskDao.checkTasks(date);
    }
}
