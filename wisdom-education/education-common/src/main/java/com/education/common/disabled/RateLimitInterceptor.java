package com.education.common.disabled;

import com.education.common.exception.BusinessException;
import com.education.common.lock.ApiLock;
import com.education.common.lock.LockManager;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;


/**
 * guava api限流
 */
@Component
@Aspect
public class RateLimitInterceptor {

    @Around("@annotation(com.education.common.disabled.RateLimitLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RateLimitLock rateLimitLock = method.getAnnotation(RateLimitLock.class);
        try {
            if (ObjectUtils.isNotEmpty(rateLimitLock)) {
                ApiLock apiLock = LockManager.getApiLock(rateLimitLock);
                try {
                    if (apiLock.tryLock()) {
                        return pjp.proceed();
                    }
                    throw new BusinessException(new ResultCode(ResultCode.FAIL, rateLimitLock.message()));
                } finally {
                    apiLock.releaseLock();
                }
            }
            return pjp.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof BusinessException) {
                throw (BusinessException) throwable;
            } else {
                throw new RuntimeException(throwable);
            }
        }
    }
}
