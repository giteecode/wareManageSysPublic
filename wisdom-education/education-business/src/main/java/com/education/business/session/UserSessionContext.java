package com.education.business.session;

import com.education.auth.AuthUtil;
import com.education.common.enums.LoginEnum;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.SystemAdmin;

/**
 * 用户session 容器工具类
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/2/24 20:12
 */
public class UserSessionContext {

    /**
     * 获取管理员session会话信息
     * @return
     */
    public static AdminUserSession getAdminUserSession() {
        return (AdminUserSession) AuthUtil.getSession(LoginEnum.ADMIN.getValue());
    }

    /**
     * 获取学员session会话信息
     * @return
     */
    public static StudentSession getStudentUserSession() {
        return (StudentSession) AuthUtil.getSession(LoginEnum.STUDENT.getValue());
    }

    public static Integer getStudentId() {
        StudentSession studentSession = getStudentUserSession();
        if (studentSession != null) {
            return studentSession.getId();
        }
        return null;
    }

    public static SystemAdmin getSystemAdmin() {
        AdminUserSession adminSession = getAdminUserSession();
        if (ObjectUtils.isNotEmpty(adminSession)) {
            return adminSession.getSystemAdmin();
        }
        return null;
    }

    public static Integer getAdminUserId() {
        if (ObjectUtils.isNotEmpty(getSystemAdmin())) {
            return getSystemAdmin().getId();
        }
        return null;
    }
}
