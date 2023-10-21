package com.education.common.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;


/**
 * RedisCacheManager 扩展
 * 实现再 @Cacheable 注解上可以配置缓存的失效时间
 */
public class ExpireRedisCacheManager extends RedisCacheManager {

    public ExpireRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        if (name.contains("#")) {
            String nameArray[] = name.split("#");
            Long timeOut = Long.valueOf(nameArray[1]);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(timeOut));
            name = nameArray[0];
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
