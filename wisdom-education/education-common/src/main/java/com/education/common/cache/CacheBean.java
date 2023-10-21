package com.education.common.cache;


import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/23 21:40
 */
public interface CacheBean {

    <T> T get(String cacheName, Object key);

    <T> T get(Object key);

    void put(Object key, Object value);

    void put(Object key, Object value, long liveSeconds);

    void put(String cacheName, Object key, Object value, long liveSeconds);

    void putValue(String cacheName, Object key, Object value);

    void expire(String cacheName, Object key, long liveSeconds);

    Long getExpire(String cacheName, Object key);
  //  void put(String cacheName, Object key, Object value);

 //   void put(String cacheName, Object key, Object value, int liveSeconds, TimeUnit timeUnit);

  //  void put(Object key, Object value, int liveSeconds, TimeUnit timeUnit);

    Collection getKeys(String cacheName);

    Collection getKeys();

    void remove(Object key);

    void remove();

    void remove(String cacheName, Object key);

    void removeAll(String cacheName);
}
