package com.test.annotation;

import java.lang.annotation.*;

/**
 * @RequestModelBeanBody 用于接收json类型参数
 * controller 方法自定义map 对象参数注入注解
 * 使用方式
 * public ModelBean getParam(@RequestModelBeanBody ModelBean params) {
 *       return params
 * }
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/4 11:40
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestModelBeanBody {
}
