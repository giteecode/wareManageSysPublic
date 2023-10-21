package com.education.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.education.auth.realm.LoginAuthRealm;
import com.education.auth.session.SessionStorage;
import com.education.auth.session.UserSession;
import com.education.auth.token.TokenFactory;
import com.education.common.constants.CacheKey;
import com.education.common.constants.CacheTime;
import com.education.common.enums.DeviceTypeEnum;
import com.education.common.exception.BusinessException;
import com.education.common.utils.RequestUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:42
 * @since version 1.0.4
 */
public class AuthUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    private static AuthRealmManager authRealmManager;

    public static <T extends UserSession> T login(LoginToken loginToken) {
        String loginType = loginToken.getLoginType();
        LoginAuthRealm loginAuthRealm = authRealmManager.getByLoginType(loginType);
        UserSession userSession = loginAuthRealm.doLogin(loginToken);
        AuthConfig authConfig = getAuthConfig();
        boolean isAllowMoreOnline = authConfig.isAllowMoreOnline();
        SessionStorage sessionStorage = getSessionStorage();

        String deviceType = loginToken.getDeviceType();
        if (StrUtil.isBlank(deviceType)) {
            boolean isMobile = RequestUtils.isMobileBrowser();
            if (isMobile || RequestUtils.isWechatBrowser()) {
                deviceType = DeviceTypeEnum.MOBILE.getValue();
            } else {
                deviceType = DeviceTypeEnum.PC.getValue();
            }
        }

        if (!DeviceTypeEnum.contains(deviceType)) {
            throw new BusinessException("非法登录设备");
        }

        userSession.setDeviceType(deviceType);
        if (!isAllowMoreOnline) {
            RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);
            RLock lock = redissonClient.getLock(CacheKey.USER_LOGIN + userSession.getId());
            lock.lock();
            try {
                checkUserIsOnline(userSession.getId(), deviceType, loginAuthRealm);
                long sessionTimeOut = loginAuthRealm.getSessionTimeOut(loginToken.isRemember());
                userSession.setLoginType(loginType);
                createUserSession(userSession, sessionStorage, sessionTimeOut);
            } finally {
                lock.unlock();
            }
        } else {
            long sessionTimeOut = loginAuthRealm.getSessionTimeOut(loginToken.isRemember());
            createUserSession(userSession, sessionStorage, sessionTimeOut);
        }
        loginAuthRealm.loadPermission(userSession);
        updateSession(userSession);
        loginAuthRealm.onLoginSuccess(userSession);
        return (T) userSession;
    }

    public static TokenFactory getTokenFactory() {
        return authRealmManager.getTokenFactory();
    }

    /**
     * 校验当前用户是否已登录
     * @param userId
     */
    private static void checkUserIsOnline(Number userId, String deviceType, LoginAuthRealm loginAuthRealm) {
        String loginType = loginAuthRealm.getLoginType();
        List<UserSession> list = getSessionStorage().getActiveSessions(loginType);
        if (CollUtil.isEmpty(list)) {
            return;
        }
        UserSession userSession = list.stream()
                .filter(session -> session.getId().equals(userId))
                .findAny().orElse(null);
        // session 不为null 并且客户端设备一样
        if (userSession != null && userSession.getDeviceType().equals(deviceType)) {
            // 删除上一个用户会话信息
            getSessionStorage().deleteSession(userSession.getToken(), loginType);
            loginAuthRealm.onRejectSession(userSession);
        }
    }

    public static AuthConfig getAuthConfig() {
        return authRealmManager.getAuthConfig();
    }


    public static void setAuthRealmManager(AuthRealmManager authRealmManager) {
        AuthUtil.authRealmManager = authRealmManager;
    }

    private static void createUserSession(UserSession userSession, SessionStorage sessionStorage, long sessionTimeOut) {
        TokenFactory tokenFactory = authRealmManager.getTokenFactory();
        String token = tokenFactory.createToken(userSession.getId(), sessionTimeOut * CacheTime.MILLIS);
        userSession.setToken(token);
        sessionStorage.saveSession(userSession, sessionTimeOut);
    }

    /**
     * 创建新的session 会话
     * @param userSession
     * @param sessionTimeOut
     */
    public static void createNewSession(UserSession userSession, String oldToken, long sessionTimeOut) {
        SessionStorage sessionStorage = getSessionStorage();
        sessionStorage.saveSession(userSession, sessionTimeOut);
        sessionStorage.deleteSession(oldToken);
    }

    public static void updateSession(UserSession userSession) {
        getSessionStorage().updateSession(userSession);
    }

    public static UserSession getSession() {
        return getSession(null);
    }

    public static UserSession getSession(String loginType) {
        String token = getTokenValue();
        if (StrUtil.isBlank(token)) {
            return null;
        }
        boolean flag = getTokenFactory().isExpiration(token);
        if (flag) {
            logger.warn("token:{}已失效", token);
            return null;
        }
        SessionStorage sessionStorage = getSessionStorage();
        return sessionStorage.getSession(token, loginType);
    }


    public static boolean hasPermission(String loginType, String permission) {
        UserSession userSession = getSession(loginType);
        List<String> permissionList = userSession.getPermissionList();
        return permissionList.contains(permission);
    }


    public static SessionStorage getSessionStorage() {
        return authRealmManager.getSessionStorage();
    }

    public static void logout() {
        logout(null);
    }

    public static void logout(String loginType) {
        String token = getTokenValue();
        if (StrUtil.isNotBlank(token)) {
            getSessionStorage().deleteSession(token, loginType);
        }
    }

    public static String getTokenValue(){
        return RequestUtils.getRequest().getHeader(getAuthConfig().getHeaders());
    }
}
