package com.education.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/13 14:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String describe();
}
