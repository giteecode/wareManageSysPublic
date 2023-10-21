package com.education.auth.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户session
 * @author zengjintao
 * @create_at 2021年11月25日 0025 16:26
 * @since version 1.0.4
 */
public abstract class UserSession implements Serializable {

    /**
     * 权限列表
     */
    private final List<String> permissionList = new ArrayList<>();

    /**
     * 用户id
     */
    private Number id;

    /**
     * token 标识
     */
    private String token;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 登陆类型
     */
    private String loginType;

    /**
     * 登录客户端设备
     */
    private String deviceType;

    /**
     * 登陆次数
     */
    private Integer loginCount = 0;

    public Date getCreateDate() {
        return createDate;
    }

    public String getLoginType() {
        return loginType;
    }


    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public UserSession(Number userId) {
        this.id = userId;
        this.createDate = new Date();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void addPermission(String permission) {
        permissionList.add(permission);
    }

    public void addPermission(Collection<String> permission) {
        permissionList.addAll(permission);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}
