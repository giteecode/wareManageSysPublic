package com.education.auth.token;


import cn.hutool.core.util.IdUtil;
import com.education.common.cache.FstRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 默认token 实现
 * @author zengjintao
 * @create_at 2021年11月26日 0026 10:51
 * @since version 1.0.4
 */
public class DefaultTokenFactory extends TokenFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTokenFactory.class);

    private final Map<String, TokenInfo> tokenMap = new HashMap<>();
    private Timer timer = new Timer();

    public DefaultTokenFactory() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Iterator<Map.Entry<String, TokenInfo>> iterator = tokenMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, TokenInfo> entry = iterator.next();
                    long expirationTime = entry.getValue().getExpirationTime();
                    if (expirationTime <= System.currentTimeMillis()) {
                        iterator.remove();
                        logger.warn("remove token:{}", entry.getKey());
                    }
                }
            }
        }, 5000);
    }

    @Override
    public String createToken(Object value, long expirationTime) {
        String token = IdUtil.simpleUUID();
        expirationTime = System.currentTimeMillis() + expirationTime;
        TokenInfo tokenInfo = new TokenInfo(token, value, expirationTime);
        tokenMap.put(token, tokenInfo);
        return token;
    }

    @Override
    public <T> T parseToken(String token) {
        TokenInfo tokenInfo = getTokenInfo(token);
        if (token != null) {
            return (T) tokenInfo.getValue();
        }
        return null;
    }

    public TokenInfo getTokenInfo(String token) {
        return tokenMap.get(token);
    }

    @Override
    public boolean isExpiration(String token) {
        TokenInfo tokenInfo = getTokenInfo(token);
        if (tokenInfo == null) {
            return true;
        }
        long expirationTime = tokenInfo.getExpirationTime();
        // 到期时间小于当前时间代表token 已失效
        if (expirationTime <= System.currentTimeMillis()) {
            tokenMap.remove(token);
            return true;
        }
        return false;
    }

    @Override
    public long getExpirationTime(String token) {
        TokenInfo tokenInfo = getTokenInfo(token);
        if (tokenInfo == null) {
            throw new RuntimeException("非法token:{" + token + "}");
        }
        return tokenInfo.getExpirationTime();
    }
}
