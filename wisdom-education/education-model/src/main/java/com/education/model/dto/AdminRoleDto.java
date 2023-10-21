package com.education.model.dto;

import com.education.model.entity.SystemAdmin;
import com.education.model.entity.SystemRole;
import java.util.List;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/14 21:47
 */
public class AdminRoleDto extends SystemAdmin {

    /**
     * 角色集合列表
     */
    private List<SystemRole> systemRoleList;

    /**
     * 角色id集合
     */
    private Set<Integer> roleIds;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<SystemRole> getSystemRoleList() {
        return systemRoleList;
    }

    public void setSystemRoleList(List<SystemRole> systemRoleList) {
        this.systemRoleList = systemRoleList;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<Integer> getRoleIds() {
        return roleIds;
    }
}
