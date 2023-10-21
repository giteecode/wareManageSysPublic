package com.education.common.cache.lock;

import com.education.common.utils.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/7 11:48
 * @since 1.0.0
 */
@Aspect
@Component
public class DistributedLockAspect {

    @Resource
    private RedissonClient redissonClient;

    @Around("@annotation(com.education.common.cache.lock.DistributedLock))")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
        if (ObjectUtils.isNotEmpty(distributedLock)) {
            String lockName = distributedLock.lockKey();
            if (ObjectUtils.isEmpty(lockName)) {
                lockName = method.getName();
            }
            AbstractDistributedLock abstractDistributedLock = new RedissonClientDistributedLock(redissonClient, lockName);
            try {
                boolean locked = abstractDistributedLock.getLock();
                if (locked) {
                    return pjp.proceed();
                }
                throw new LockPermissionException("lock acquisition timeout");
            } finally {
                abstractDistributedLock.release();
            }
        }
        return pjp.proceed();
    }
}
