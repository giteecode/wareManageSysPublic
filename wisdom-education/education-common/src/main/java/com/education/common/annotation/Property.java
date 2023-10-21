package com.education.common.annotation;

import java.lang.annotation.*;

/**
 * 参数属性注解
 * 例如 {
 *     name: ''
 *     userInfo: {
 *         name: '',
 *         age: ''
 *     }
 * }
 * 此时需要对对象中的userInfo对象中的属性再次进行校验
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/24 9:50
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Property {

    /**
     * 校验字段名
     * @return
     */
    String name() default "";

    /**
     * 返回提示
     * @return
     */
    String message() default "";
}
