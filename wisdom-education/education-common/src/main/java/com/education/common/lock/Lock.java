package com.education.common.lock;


/**
 * api 限流锁
 */
public interface Lock {

    /**
     * 获取锁
     * @return
     */
    boolean tryLock() throws Exception;

    /**
     * 释放锁
     */
    default void releaseLock() {

    }
}
