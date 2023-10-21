package com.education.auth.session;

import java.util.Collections;
import java.util.List;

/**
 * session 会话缓存接口
 * @author zengjintao
 * @create_at 2021年11月25日 0025 17:14
 * @since version 1.0.4
 */
public interface SessionStorage {

    /**
     * 保存session
     * @param userSession
     */
    void saveSession(UserSession userSession, long sessionTimeOut);

    /**
     * 获取会话列表
     * @return
     */
    default List<UserSession> getActiveSessions() {
        return getActiveSessions(null);
    }

    /**
     * 根据登录类型获取会话列表
     * @return
     */
    default List<UserSession> getActiveSessions(String loginType) {
        return Collections.emptyList();
    }

    /**
     * 更新会话缓存
     * @param userSession
     */
    void updateSession(UserSession userSession);

    /**
     * 获取用户会话
     * @param token
     * @return
     */
    UserSession getSession(String token);

    /**
     * 根据登陆类型获取会话
     * @param token
     * @param loginType
     * @return
     */
    UserSession getSession(String token, String loginType);

    /**
     * 删除session 会话
     * @param token
     */
    void deleteSession(String token);

    /**
     * 根据登陆类型删除session 会话
     * @param token
     * @param loginType
     */
    void deleteSession(String token, String loginType);
}
