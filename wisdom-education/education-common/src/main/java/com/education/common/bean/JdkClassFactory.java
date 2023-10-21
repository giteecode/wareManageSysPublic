package com.education.common.bean;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 默认bean实例工厂
 * @author zengjintao
 * @version 1.0
 * @create 2018/10/8 16:51
 **/
@SuppressWarnings("serial")
public class JdkClassFactory extends AbstractClassFactory {

    @SuppressWarnings("unchecked")
	@Override
    public <T> T createBean(Class<T> classType) {
        Class<?> createClass = parseInterface(classType);
        try {
            Constructor<?> constructor = createClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("can not create bean: " + classType, e);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> T createBean(Class<T> classType, List<Object> params) {
        try {
            Class<?> createClass = parseInterface(classType);
            Constructor<T>[] constructors = (Constructor<T>[]) createClass.getDeclaredConstructors();
            for (Constructor<T> constructor : constructors) {
                if (constructor.getParameterCount() == params.size()) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(params.toArray());
                }
            }
        } catch (Exception e) {
            throw new BeansException("can not create bean: " + classType, e);
        }
        return null;
    }
}
