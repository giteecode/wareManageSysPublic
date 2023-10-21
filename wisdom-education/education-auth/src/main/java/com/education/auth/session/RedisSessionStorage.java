package com.education.auth.session;

import cn.hutool.core.util.StrUtil;
import com.education.common.cache.CacheBean;
import com.jfinal.kit.HashKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 09:14
 * @since version 1.0.4
 */
public class RedisSessionStorage extends AbstractSessionStorage {

    private final CacheBean cacheBean;

    public RedisSessionStorage(CacheBean cacheBean, String sessionIdPrefix) {
        super(sessionIdPrefix);
        this.cacheBean = cacheBean;
    }

    @Override
    public void updateSession(UserSession userSession) {
        String cacheName = getCacheName(userSession.getLoginType());
        String key = hashToken(userSession.getToken());
        long expire = cacheBean.getExpire(cacheName, key);
        cacheBean.put(cacheName, key, userSession, expire);
    }

    @Override
    public UserSession getSession(String token) {
        return getSession(token, null);
    }

    @Override
    public UserSession getSession(String token, String loginType) {
        return cacheBean.get(getCacheName(loginType), hashToken(token));
    }

    @Override
    public void deleteSession(String token) {
        deleteSession(token, null);
    }

    private String hashToken(String token) {
        return HashKit.md5(token);
    }

    @Override
    public void deleteSession(String token, String loginType) {
        cacheBean.remove(getCacheName(loginType), hashToken(token));
    }

    private String getCacheName(String loginType) {
        String cacheName = getSessionIdPrefix();
        if (StrUtil.isNotBlank(loginType)) {
            cacheName += StrUtil.COLON + loginType;
        }
        return cacheName;
    }


    @Override
    public void saveSession(UserSession userSession, long sessionTimeOut) {
        String cacheName = getCacheName(userSession.getLoginType());
        cacheBean.put(cacheName, hashToken(userSession.getToken()), userSession, (int) sessionTimeOut);
    }

    @Override
    public List<UserSession> getActiveSessions() {
        return getActiveSessions(null);
    }

    @Override
    public List<UserSession> getActiveSessions(String loginType) {
        List<UserSession> userSessionList = new ArrayList<>();
        Set<String> tokenList = (Set<String>) cacheBean.getKeys(getCacheName(loginType));
        for (String token : tokenList) {
            UserSession userSession = cacheBean.get(token);
            if (userSession != null) {
                userSessionList.add(userSession);
            }
        }
        return userSessionList;
    }
}
