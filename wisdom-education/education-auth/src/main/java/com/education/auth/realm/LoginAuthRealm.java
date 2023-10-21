package com.education.auth.realm;

import com.education.auth.LoginToken;
import com.education.auth.session.UserSession;
import com.education.common.constants.CacheTime;

/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:25
 * @since version 1.0.4
 */
public interface LoginAuthRealm<T extends UserSession> {


    /**
     * 用户登陆
     * @param loginToken
     * @return
     */
    T doLogin(LoginToken loginToken);

    /**
     * 获取登陆类型
     * @return
     */
    default String getLoginType() {
        return getClass().getSimpleName();
    }

    /**
     * 登录成功回调方法
     * @param userSession
     */
    default void onLoginSuccess(T userSession) {
        System.out.println("账号:【" + userSession.getId() + "】登录成功");
    }

    /**
     * 退出成功回调事件
     * @param userSession
     */
    default void onLogoutSuccess(T userSession) {
        System.out.println("账号:【" + userSession.getId() + "】注销成功");
    }

    /**
     * 用户账号被顶下线事件回调
     * @param userSession
     */
    default void onRejectSession(T userSession) {
        System.out.println("剔除会话id:【" + userSession.getToken() + "】成功");
    }

    /**
     * 登录失败事件回调
     * @param username
     */
    default void onLoginFail(String username, Exception e) {
        System.err.println("账号:【" + username + "】登录失败");
        e.printStackTrace();
    }


    /**
     * 加载账号权限
     * @param userSession
     */
    default void loadPermission(T userSession) {
        System.out.println("加载权限方法");
    }

    /**
     * 登录之后可设置token 会话有效期
     * @param rememberMe
     * 单位 秒
     */
    default long getSessionTimeOut(boolean rememberMe) {
        if (rememberMe) {
            // 缓存一周
            return CacheTime.ONE_WEEK_SECOND;
        }
        // 缓存一小时
        return CacheTime.ONE_HOUR_SECOND;
    }
}
