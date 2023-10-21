package com.education.common.enums;

import java.util.Arrays;

public enum PlatformTypeEnum {
    WEB_ADMIN(1, "后台管理系统"), WEB_FRONT(2, "学员端");
    private Integer code;
    private String name;

    PlatformTypeEnum(Integer code, String name) {
        this.name = name;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getByCode(Integer code) {
        PlatformTypeEnum platformTypeEnum = Arrays.stream(values()).filter(item -> item.code.equals(code)).findAny().orElse(null);
        return platformTypeEnum == null ? "" : platformTypeEnum.name;
    }
}