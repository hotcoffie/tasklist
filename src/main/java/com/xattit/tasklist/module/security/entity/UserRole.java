package com.xattit.tasklist.module.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * security_user_role
 *
 * @author
 */
@Data
public class UserRole implements Serializable {

    public static final int DEFAULT_ROLE_ID = 2;

    private Integer id;

    private Integer userId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;
}
