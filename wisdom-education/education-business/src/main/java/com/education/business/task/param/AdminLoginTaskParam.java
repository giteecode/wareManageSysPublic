package com.education.business.task.param;

import com.education.model.entity.SystemAdmin;


/**
 * @author zjt
 * @create_at 2022年11月24日 0024 14:24
 * @since 1.0.6
 */
public class AdminLoginTaskParam extends TaskParam {

    /**
     * 管理员信息
     */
    private SystemAdmin systemAdmin;

    /**
     * 新登录的ip地址
     */
    private String newLoginIp;

    public AdminLoginTaskParam(String queueName) {
        super(queueName);
    }

    public SystemAdmin getSystemAdmin() {
        return systemAdmin;
    }

    public void setSystemAdmin(SystemAdmin systemAdmin) {
        this.systemAdmin = systemAdmin;
    }

    public String getNewLoginIp() {
        return newLoginIp;
    }

    public void setNewLoginIp(String newLoginIp) {
        this.newLoginIp = newLoginIp;
    }
}
