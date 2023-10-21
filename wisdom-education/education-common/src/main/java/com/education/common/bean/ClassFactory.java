package com.education.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * bean工厂接口
 * @author zengjintao
 * @version 1.0
 * @create 2018/10/8 16:49
 **/
public interface ClassFactory extends Serializable {

    /**
     * 根据无参构造方法创建实例
     * @param classType
     * @param <T>
     * @return
     */
    public <T> T createBean(Class<T> classType);

    /**
     * 根据无参构造方法创建单实例
     * @param classType
     * @param <T>
     * @return
     */
    public <T> T createSingletonBean(Class<T> classType);

    /**
     * 根据有参构造方法创建单实例
     * @param classType
     * @param params
     * @param <T>
     * @return
     */
    public <T> T createBean(Class<T> classType, List<Object> params);

    /**
     * 根据有参构造方法创建单实例
     * @param classType
     * @param params
     * @param <T>
     * @return
     */
    public <T> T createSingletonBean(Class<T> classType, List<Object> params);
}
