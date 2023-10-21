package com.education.common.cache.lock;

import java.lang.annotation.*;

/**
 * 分布式锁注解
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/26 19:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DistributedLock {

    /**
     * 获取锁的key
     * @return
     */
    String lockKey() default "";

    /**
     * 获取锁等待时间, 默认最多等待5秒
     * @return
     */
    long timeOut() default 5;
}
