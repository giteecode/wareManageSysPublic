package com.education.auth.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地会话缓存实现类
 * @author zengjintao
 * @create_at 2021年11月26日 0026 09:37
 * @since version 1.0.4
 */
public class LocalSessionStorage extends AbstractSessionStorage {

    private final Map<String, UserSession> sessionMap = new ConcurrentHashMap<>();
    private final Map<String, Long> expireSessionMap = new ConcurrentHashMap<>();

    public LocalSessionStorage(String sessionIdPrefix) {
        super(sessionIdPrefix);
        initClearSessionTask();
    }

    public LocalSessionStorage() {
        initClearSessionTask();
    }

    private void initClearSessionTask() {

    }

    @Override
    public void saveSession(UserSession userSession, long sessionTimeOut) {
        String sessionId = userSession.getToken();
        sessionMap.put(sessionId, userSession);
        expireSessionMap.put(sessionId, sessionTimeOut);
    }

    @Override
    public void updateSession(UserSession userSession) {
        sessionMap.put(userSession.getToken(), userSession);
    }

    @Override
    public UserSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public UserSession getSession(String sessionId, String loginType) {
        return null;
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    @Override
    public void deleteSession(String sessionId, String loginType) {

    }
}
