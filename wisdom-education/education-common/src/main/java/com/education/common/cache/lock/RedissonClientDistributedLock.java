package com.education.common.cache.lock;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/26 19:49
 */
public class RedissonClientDistributedLock extends AbstractDistributedLock {

    private RedissonClient redissonClient;
    protected long expireMsecs = 60; // 获得锁后默认解锁时间，防止redis挂掉而导致锁无法释放
    private static final Logger logger = LoggerFactory.getLogger(RedissonClientDistributedLock.class);

    public RedissonClientDistributedLock(RedissonClient redissonClient, String lockName) {
        super(lockName);
        this.redissonClient = redissonClient;
    }

    public RedissonClientDistributedLock(RedissonClient redissonClient, String lockName, long timeOut) {
        super(lockName, timeOut);
        this.redissonClient = redissonClient;
    }


    @Override
    public void release() {
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
    }

    @Override
    public boolean getLock() {
        RLock lock = redissonClient.getLock(lockName);
        if (this.timeOut > 0) {
            try {
                return lock.tryLock(this.timeOut, expireMsecs, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return lock.tryLock();
    }
}
