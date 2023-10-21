package com.education.common.cache.lock;

/**
 *
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/7 14:04
 * @since 1.0.0
 */
public class LockPermissionException extends RuntimeException {

    public LockPermissionException() {
        super();
    }

    public LockPermissionException(String message) {
        super(message);
    }

    public LockPermissionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
