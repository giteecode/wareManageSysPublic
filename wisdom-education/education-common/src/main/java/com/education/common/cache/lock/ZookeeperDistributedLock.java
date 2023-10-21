package com.education.common.cache.lock;


/**
 * Zookpeer 分布式锁
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/26 20:02
 */
public class ZookeeperDistributedLock extends AbstractDistributedLock {

    public ZookeeperDistributedLock(String lockName, long timeOut) {
        super(lockName, timeOut);
    }

    @Override
    public void release() {

    }

    @Override
    public boolean getLock() {
        return false;
    }
}
