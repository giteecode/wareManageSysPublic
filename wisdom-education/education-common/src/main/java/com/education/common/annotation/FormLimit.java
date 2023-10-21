package com.education.common.annotation;

import java.lang.annotation.*;

/**
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/21 15:45
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormLimit {

    int timeOut() default 5; // 默认五秒内再次请求接口似为表单重复提交

    int errorCode() default 0; //返回码

    String message() default "";
}
