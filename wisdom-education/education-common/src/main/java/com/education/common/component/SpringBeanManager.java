package com.education.common.component;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/11/2422:18
 */
@Component
@Lazy(false)
public class SpringBeanManager implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) SpringBeanManager.applicationContext.getBean(clazz);
    }

    @Override
    public void destroy() throws Exception {
        if (applicationContext != null) {
            applicationContext = null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanManager.applicationContext = applicationContext;
    }
}
