package com.education.common.cache;


import java.util.Collection;


/**
 * 基于Ehcache 缓存
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/23 21:11
 */
public class EhcacheBean implements CacheBean {


    @Override
    public <T> T get(String cacheName, Object key) {
        return null;
    }

    @Override
    public <T> T get(Object key) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {

    }

    @Override
    public void put(Object key, Object value, long liveSeconds) {

    }

    @Override
    public void put(String cacheName, Object key, Object value, long liveSeconds) {

    }

    @Override
    public void putValue(String cacheName, Object key, Object value) {

    }

    @Override
    public void expire(String cacheName, Object key, long liveSeconds) {

    }

    @Override
    public Long getExpire(String cacheName, Object key) {
        return null;
    }

    @Override
    public Collection getKeys(String cacheName) {
        return null;
    }

    @Override
    public Collection getKeys() {
        return null;
    }

    @Override
    public void remove(Object key) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void remove(String cacheName, Object key) {

    }

    @Override
    public void removeAll(String cacheName) {

    }
}
