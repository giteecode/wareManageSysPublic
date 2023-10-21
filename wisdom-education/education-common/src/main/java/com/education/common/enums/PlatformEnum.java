package com.education.common.enums;

/**
 * @author zengjintao
 * @create_at 2021/10/23 11:30
 * @since version 1.0.3
 */
public enum PlatformEnum {

    SYSTEM_ADMIN(1,"educationAdmin", "管理后台"),
    SYSTEM_STUDENT(2,"educationStudent", "学生端"),
    MOBILE_H5(3,"educationMobile", "移动端");

    private Integer code;
    private String headerValue;
    private String name;

    PlatformEnum(Integer code, String headerValue, String name) {
        this.code = code;
        this.headerValue = headerValue;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public String getName() {
        return name;
    }
}
