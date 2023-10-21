package com.education.common.model;


import com.alibaba.fastjson.util.TypeUtils;

import java.util.*;

/**
 * 封装map 类型转换
 * @author zengjintao
 * @version 1.0
 * @create 2018/11/8 11:26
 **/
public class ModelBeanMap extends LinkedHashMap {

    public static ModelBeanMap create() {
        return new ModelBeanMap();
    }

    public static ModelBeanMap by(Object key, Object value) {
        return create().put(key, value);
    }

    public ModelBeanMap set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getStr(Object key) {
        Object s = get(key);
        return TypeUtils.castToString(s);
    }

    public ModelBeanMap put(Object key, Object vale) {
        super.put(key, vale);
        return this;
    }

    public Integer getInt(Object key) {
        Object value = get(key);
        return TypeUtils.castToInt(value);
    }

    public Long getLong(Object key) {
        Object value = get(key);
        return TypeUtils.castToLong(value);
    }

    public java.math.BigInteger getBigInteger(Object key) {
        Object value = get(key);
        return TypeUtils.castToBigInteger(value);
    }

    public java.util.Date getDate(Object key) {
        Object value = get(key);
        return TypeUtils.castToDate(value);
    }

    public java.sql.Time getTime(Object key) {
        return (java.sql.Time)get(key);
    }

    public java.sql.Timestamp getTimestamp(Object key) {
        return (java.sql.Timestamp)get(key);
    }


    public Double getDouble(Object key) {
        Object value = get(key);
        return TypeUtils.castToDouble(value);
    }

    public Float getFloat(Object key) {
        Object value = get(key);
        return TypeUtils.castToFloat(value);
    }

    public Short getShort(Object key) {
        Object value = get(key);
        return TypeUtils.castToShort(value);
    }

    public Boolean getBoolean(Object key) {
        Object value = get(key);
        return TypeUtils.castToBoolean(value);
    }

    public java.math.BigDecimal getBigDecimal(Object key) {
        Object value = get(key);
        return TypeUtils.castToBigDecimal(value);
    }

    public byte[] getBytes(Object key) {
        Object value = get(key);
        return TypeUtils.castToBytes(value);
    }

    public <T> List<T> getList(Object key) {
        Object object = get(key);
        return object == null ? null : (List<T>) get(key);
    }

    @Deprecated
    public List<ModelBeanMap> getModelBeanMapList(Object key) {
        Object object = get(key);
        if (!(object instanceof List)) {
            throw new ClassCastException(key + "values can not cast to List" );
        }
        List list = (List)object;
        List<ModelBeanMap> result = new ArrayList<>();
        list.forEach(item -> {
            if (item instanceof Map) {
                ModelBeanMap modelBeanMap = new ModelBeanMap();
                Map data = (Map)item;
                data.keySet().forEach(mapKey -> {
                    modelBeanMap.put(mapKey, data.get(mapKey));
                });
                result.add(modelBeanMap);
            }
        });
        return result;
    }

    public <T> Set<T> getSet(Object key) {
        return (Set<T>) get(key);
    }
}
