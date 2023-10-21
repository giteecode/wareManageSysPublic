package com.education.business.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.business.task.TaskManager;
import com.education.common.cache.CacheBean;
import com.education.common.upload.FileUpload;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * service 基类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 10:40
 */
public abstract class BaseService<M extends BaseMapper<T>, T> extends CrudService<M, T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected TaskManager taskManager;
    @Autowired
    @Qualifier("redisCacheBean")
    protected CacheBean cacheBean;
    @Autowired
    protected HttpServletRequest request;
    @Resource
    protected RedisTemplate redisTemplate;
    @Resource
    protected RedissonClient redissonClient;
    @Resource
    protected FileUpload fileUpload;

}
