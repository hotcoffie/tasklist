package com.xattit.tasklist.module.task.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * task
 *
 * @author
 */
@Data
public class Task implements Serializable {
    private Integer id;

    private Integer userId;

    private String thisWeek;

    private String afterWeek;

    private Character checked;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
