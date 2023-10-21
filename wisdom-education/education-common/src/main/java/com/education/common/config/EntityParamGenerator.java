package com.education.common.config;


import com.jfinal.json.Jackson;
import com.jfinal.kit.HashKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/29 16:07
 */
@Component
public class EntityParamGenerator implements KeyGenerator {

    @Autowired
    private KeyGenerator keyGenerator;

    private final Jackson jackJson = new Jackson();

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length >= 1) {
            String methodName = method.getName();
            params[params.length - 1] = methodName;
            String key = jackJson.toJson(params);
            return HashKit.md5(key);
        }
        return keyGenerator.generate(target, method, params);
    }
}
