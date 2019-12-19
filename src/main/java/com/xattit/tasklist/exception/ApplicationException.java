package com.xattit.tasklist.exception;

/**
 * Description 应用异常
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/19 21:01
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String msg) {
        super(msg);
    }
}
