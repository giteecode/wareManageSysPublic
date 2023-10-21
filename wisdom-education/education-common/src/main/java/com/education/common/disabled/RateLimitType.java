package com.education.common.disabled;

public enum RateLimitType {

    /**
     * 令牌桶，通过 guava 的 RateLimiter 来实现
     */
    TOKEN_BUCKET,

    /**
     * 并发量，通过 Semaphore 来实现
     */
    CONCURRENCY;
}
