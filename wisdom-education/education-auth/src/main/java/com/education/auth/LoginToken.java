package com.education.auth;

/**
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:41
 * @since version 1.0.4
 */
public class LoginToken {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    public String password;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 登录设备
     */
    private String deviceType;

    /**
     * 是否记住密码
     */
    private final boolean remember;

    public LoginToken(String username, String password) {
        this(username, password, null,false, null);
    }

    public LoginToken(String username, String password, String loginType) {
        this(username, password, loginType, false, null);
    }

    public LoginToken(String username, String password, boolean remember) {
        this(username, password, null, remember, null);
    }

    public LoginToken(String username, String password, String loginType, boolean remember, String deviceType) {
        this.username = username;
        this.password = password;
        this.loginType = loginType;
        this.remember = remember;
        this.deviceType = deviceType;
    }

    public String getUsername() {
        return username;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
