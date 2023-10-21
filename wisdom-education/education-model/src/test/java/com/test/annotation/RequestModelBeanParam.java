package com.test.annotation;

import java.lang.annotation.*;

/**
 * controller 方法自定义map 对象参数注入注解
 * 使用方式
 * public ModelBean getParam(@RequestModelBeanParam ModelBean params) {
 *       return params
 * }
 * map实体类
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/11/30 18:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestModelBeanParam {


}
