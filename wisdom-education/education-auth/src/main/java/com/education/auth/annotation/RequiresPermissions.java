package com.education.auth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zengjintao
 * @create_at 2021年11月26日 0026 17:07
 * @since version 1.0.4
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {

    String[] value();

    Logical logical() default Logical.AND;

    String loginType() default "admin";
}
