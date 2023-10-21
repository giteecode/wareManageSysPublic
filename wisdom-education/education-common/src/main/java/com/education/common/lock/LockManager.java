package com.education.common.lock;

import com.education.common.disabled.RateLimitLock;

public class LockManager {

    public static ApiLock getApiLock(RateLimitLock rateLimitLock) {
        switch (rateLimitLock.rateLimitType()) {
            case CONCURRENCY:
                return new SemaphoreApiLock(rateLimitLock);
            case TOKEN_BUCKET:
                return new RateLimiterApiLock(rateLimitLock);
        }
        return new SemaphoreApiLock(rateLimitLock);
    }
}
