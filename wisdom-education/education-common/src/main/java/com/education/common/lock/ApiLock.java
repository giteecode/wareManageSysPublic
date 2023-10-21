package com.education.common.lock;

import com.education.common.disabled.RateLimitLock;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public abstract class ApiLock implements Lock {

    private static final Map<String, Semaphore> semaphoreCache = new ConcurrentHashMap<>();
    private static final Map<String, RateLimiter> rateLimiterCache = new ConcurrentHashMap<>();

    protected RateLimitLock rateLimitLock;

    protected boolean locked = false;

    public ApiLock(RateLimitLock rateLimitLock) {
        this.rateLimitLock = rateLimitLock;
    }

    public boolean isLocked() {
        return locked;
    }

    public static RateLimiter getOrCreateRateLimiter(String resource, int rate) {
        RateLimiter limiter = rateLimiterCache.get(resource);
        if (limiter == null) {
            synchronized (resource.intern()) {
                limiter = rateLimiterCache.get(resource);
                if (limiter == null) {
                    limiter = RateLimiter.create(rate);
                    rateLimiterCache.put(resource, limiter);
                }
            }
        }
        return limiter;
    }

    public static Semaphore getOrCreateSemaphore(String resource, int rate) {
        Semaphore semaphore = semaphoreCache.get(resource);
        if (semaphore == null) {
            synchronized (resource.intern()) {
                semaphore = semaphoreCache.get(resource);
                if (semaphore == null) {
                    semaphore = new Semaphore(rate);
                    semaphoreCache.put(resource, semaphore);
                }
            }
        }
        return semaphore;
    }
}
