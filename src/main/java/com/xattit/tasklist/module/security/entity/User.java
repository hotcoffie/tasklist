package com.xattit.tasklist.module.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * security_user
 *
 * @author
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String password;

    private String name;

    private Date createTime;

    private Date updateTime;

    private List<Role> roles;
}
