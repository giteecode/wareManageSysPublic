package com.education.common.disabled;


import java.lang.annotation.*;

/**
 * @descript: guava api限流锁注解
 * @Auther: zengjintao
 * @Date: 2020/1/2 13:39
 * @Version:2.1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimitLock {

    /**
     * 标识 指定sec时间段内的访问次数限制
     * @return
     */
    int limit() default 1;

    /**
     * 获取令牌的等待时间  默认1
     * @return
     */
    int sec() default 1;

    /**
     *  限流资源 默认取接口地址
     */
    String urlKey() default "";

    /**
     * 类型
     * @return
     */
    RateLimitType rateLimitType() default RateLimitType.TOKEN_BUCKET;


    /**
     * 限流消息提示
     * @return
     */
    String message() default "操作过于频繁，先休息一会吧";
}
