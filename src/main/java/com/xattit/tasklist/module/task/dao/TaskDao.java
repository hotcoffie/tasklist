package com.xattit.tasklist.module.task.dao;

import com.xattit.tasklist.module.task.entity.Task;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 13:57
 */
@Mapper
public interface TaskDao {
    @Select("<script>" +
            "select t2.name, t1.this_week, t1.after_week, t1.checked " +
            "from task t1 join security_user t2 on t1.user_id=t2.id " +
            "<where>" +
            "   <if test=\"userId!=null and userId!=''\"> " +
            "   t1.user_id=#{userId} " +
            "   </if> " +
            "   and t1.create_time=#{date} " +
            "   order by t2.name asc " +
            "</where>" +
            "</script>")
    List<Task> search(@Param("userId") Integer userId, @Param("date") Date date);

    @Select("select * from task " +
            "where user_id=#{userId} and create_time=#{createTime}")
    List<Task> findByUserIdAndCreateTime(Integer userId, Date createTime);

    @Insert("insert into task( " +
            "user_id, create_time, this_week, after_week " +
            ") " +
            "values( " +
            "#{task.userId}, #{task.createTime}, #{task.thisWeek}, #{task.afterWeek} " +
            ")")
    void add(@Param("task") Task task);

    @Select("select * from task " +
            "where id=#{id}")
    Task findById(Integer id);

    @Update("update task " +
            "set this_week=#{task.thisWeek}, after_week=#{task.afterWeek} " +
            "where id=#{task.id}")
    void modify(@Param("task") Task task);

    @Update("update task " +
            "set checked='1' " +
            "where create_time=#{date}")
    void checkTasks(Date date);
}
