package com.education.common.disabled;

import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @descript: redis 限流拦截器
 * @Auther: zengjintao
 * @Date: 2020/1/2 13:43
 * @Version:2.1.0
 */
@Component
@Aspect
public class RedisLimitInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.education.common.disabled.LimitLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LimitLock limitLock = method.getAnnotation(LimitLock.class);
        try {
            if (ObjectUtils.isNotEmpty(limitLock)) {
                int limit = limitLock.limit();
                int sec = limitLock.sec();
                String key = limitLock.limitType().name();
                LimitKey limitKey = new UrlLimitKey();
                Integer maxLimit = (Integer) redisTemplate.opsForValue().get(key);
                System.err.println(maxLimit);
                if (maxLimit == null) {
                    redisTemplate.opsForValue().set(limitKey.getTargetUrl(), 1, sec, TimeUnit.SECONDS);
                } else if (maxLimit < limit){
                    redisTemplate.opsForValue().set(limitKey.getTargetUrl(), maxLimit + 1, sec, TimeUnit.SECONDS);
                } else {
                    throw new BusinessException(new ResultCode(ResultCode.FAIL, limitLock.message()));
                }
            }
            return pjp.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof BusinessException) {
                throw (BusinessException)throwable;
            } else {
                throw new RuntimeException(throwable);
            }
        }
    }
}