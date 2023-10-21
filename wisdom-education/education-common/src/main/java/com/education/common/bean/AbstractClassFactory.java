package com.education.common.bean;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean工厂抽象接口接口
 * @author zengjintao
 * @version 1.0
 * @create 2018/10/8 16:51
 **/
public abstract class AbstractClassFactory implements ClassFactory {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用于存放无参构造的bean实例
    private final Map<Class<?>, Object> singletonBeanMap = new ConcurrentHashMap<>();
    //用于存放有参构造的bean实例
    private final Map<Class<?>, Object> singletonBeanParamsMap = new ConcurrentHashMap<>();

    protected Class<?> parseInterface(Class<?> classType) {
        if (classType == List.class || classType == Collection.class || classType == Iterable.class) {
            return ArrayList.class;
        } else if (classType == Map.class) {
            return HashMap.class;
        } else if (classType == SortedSet.class) { // issue #510 Collections Support
            return TreeSet.class;
        } else if (classType == Set.class) {
            return HashSet.class;
        } else {
            return classType;
        }
    }

    protected void putBean(Class<?> classType, Object beanValue) {
        singletonBeanMap.put(classType, beanValue);
    }

    protected Object getBean(Class<?> classType) {
        return singletonBeanMap.get(classType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> T createSingletonBean(Class<T> classType) {
        Object object = singletonBeanMap.get(classType);
        if (object == null) {
            try {
                synchronized (classType) {
                    object = singletonBeanMap.get(classType);
                    if (object == null) {
                        object = createBean(classType);
                        singletonBeanMap.put(classType, object);
                    }
                }
            } catch (Exception e) {
            	throw new BeansException("can not create bean: " + classType, e);
            }
        }
        return (T) object;
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> T createSingletonBean(Class<T> classType, List<Object> params) {
        Object object = singletonBeanParamsMap.get(classType);
        if (object == null) {
            try {
                synchronized (classType) {
                    object = singletonBeanParamsMap.get(classType);
                    if (object == null) {
                        object = createBean(classType, params);
                        singletonBeanParamsMap.put(classType, object);
                    }
                }
            } catch (Exception e) {
                throw new BeansException("can not create bean: " + classType, e);
            }
        }
        return (T) object;
    }
}
