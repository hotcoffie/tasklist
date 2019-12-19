package com.xattit.tasklist.vo;

import lombok.Data;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 15:54
 */
@Data
public class Result {
    private static final int fail = 0;
    private static final int success = 1;

    private int code;
    private String msg;
    private Object date;

    private Result(int code, String msg, Object date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public static Result ok() {
        return new Result(success, null, "OK");
    }

    public static Result success(Object date) {
        return new Result(success, null, date);
    }

    public static Result fail(String msg) {
        return new Result(fail, msg, null);
    }
}
