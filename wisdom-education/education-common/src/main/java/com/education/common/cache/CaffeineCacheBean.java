package com.education.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于Caffeine 实现的本地缓存
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/17 10:30
 */
public class CaffeineCacheBean implements CacheBean {

    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private static final String MAIN_CACHE = "main_cache";

    @Override
    public <T> T get(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        CaffeineCacheElement caffeineCacheElement = (CaffeineCacheElement) cache.getIfPresent(key);
        if (caffeineCacheElement == null) {
            return null;
        }

        if (caffeineCacheElement.isTimeOut()) {
            cache.invalidate(key);
            return null;
        }
        return (T) caffeineCacheElement.getValue();
    }

    private Cache getCacheOnly(String cacheName) {
        return cacheMap.get(cacheName);
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            synchronized (CaffeineCacheBean.class) {
                cache = cacheMap.get(cacheName);
                if (cache == null) {
                    cache = this.createCache();
                    cacheMap.put(cacheName, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public <T> T get(Object key) {
        return this.get(MAIN_CACHE, key);
    }


    @Override
    public void put(Object key, Object value) {
        Cache cache = getCache(MAIN_CACHE);
        cache.put(key, new CaffeineCacheElement(value));
    }

    @Override
    public void put(Object key, Object value, long liveSeconds) {
        this.put(MAIN_CACHE, key, value, liveSeconds);
    }

    @Override
    public void put(String cacheName, Object key, Object value, long liveSeconds) {
        Cache cache = getCache(cacheName);
        CaffeineCacheElement caffeineCacheElement = new CaffeineCacheElement(value);
        if (liveSeconds > 0) {
            caffeineCacheElement.setLiveSeconds((int) liveSeconds);
        }
        caffeineCacheElement.setCreateTime(new Date());
        cache.put(key, caffeineCacheElement);
    }

    @Override
    public void putValue(String cacheName, Object key, Object value) {
        this.put(cacheName, key, value, 0);
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
        Cache cache = getCache(cacheName);
        return cache.asMap().keySet();
    }

    @Override
    public Collection getKeys() {
        return this.cacheMap.keySet();
    }

    @Override
    public void remove(Object key) {
        Cache cache = getCacheOnly(MAIN_CACHE);
        if (cache != null) {
            cache.invalidate(key);
        }
    }

    @Override
    public void remove() {
        Set<String> keys = this.cacheMap.keySet();
        keys.forEach(key -> {
            Cache cache = getCacheOnly(key);
            cache.invalidateAll();
            this.cacheMap.remove(key);
        });
    }

    @Override
    public void remove(String cacheName, Object key) {
        Cache cache = getCacheOnly(cacheName);
        if (cache != null) {
            cache.invalidate(key);
        }
    }

    @Override
    public void removeAll(String cacheName) {
        Cache cache = getCacheOnly(cacheName);
        if (cache != null) {
            cache.invalidateAll();
        }
    }

    private Cache createCache() {
        return Caffeine.newBuilder().build();
    }
}
