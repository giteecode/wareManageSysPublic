package com.education.common.lock;

import com.education.common.disabled.RateLimitLock;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 限流
 */
public class SemaphoreApiLock extends ApiLock {

    private Semaphore semaphore;

    public SemaphoreApiLock(RateLimitLock rateLimitLock) {
        super(rateLimitLock);
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public boolean tryLock() throws Exception {
        Semaphore semaphore = getOrCreateSemaphore(rateLimitLock.urlKey(), rateLimitLock.limit());
        this.setSemaphore(semaphore);
        if (semaphore.tryAcquire(rateLimitLock.sec())) {
            this.locked = true;
        }
        return this.locked;
    }

    @Override
    public void releaseLock() {
        if (this.locked) {
            this.semaphore.release(rateLimitLock.sec());
        }
    }
}
