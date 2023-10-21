package com.education.auth.exception;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 17:28
 * @since version 1.0.4
 */
public class PermissionException extends RuntimeException {

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException() {
    }


    /**
     * 异常构造函数会调用 fillInStackTrace() 构建整个调用栈，消耗较大
     * 而 PermissionException 无需使用调用栈信息，覆盖此方法用于提升性能
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
