package com.education.common.enums;

/**
 * @author zengjintao
 * @create_at 2021年11月27日 0027 11:29
 * @since version 1.0.4
 */
public enum LoginEnum {

    ADMIN("admin", "管理员登录"),
    STUDENT("student", "学员登录");

    private String value;
    private String desc;

    LoginEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }
}
