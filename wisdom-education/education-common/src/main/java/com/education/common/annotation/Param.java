package com.education.common.annotation;

import java.lang.annotation.*;

/**
 * 参数校验注解
 * @author zengjintao
 * @version 1.0
 * @create 2018/12/22 16:34
 **/
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {

    int errorCode() default 0; //返回码

    /**
     * 校验字段名
     * @return
     */
    String name();

    /**
     * 返回提示
     * @return
     */
    String message();

    /**
     * 需要校验的字段
     * @return
     */
    Property[] property() default {};

    /**
     * 校验类型
     * @return
     */
    ValidateType validateType() default ValidateType.SAVE;

    /**
     * 参数校验正则
     * @return
     */
    String regexp() default "";

    /**
     * 参数校验正则校验失败错误提示
     * @return
     */
    String regexpMessage() default "";
}