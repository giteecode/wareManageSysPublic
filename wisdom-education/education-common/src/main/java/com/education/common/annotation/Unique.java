package com.education.common.annotation;

import java.lang.annotation.*;

/**
 * 实体类唯一约束注解
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/29 11:27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {

    String value() default "";
}
