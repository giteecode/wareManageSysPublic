package com.education.common.enums;

import java.util.Arrays;

/**
 * @author zjt
 * @create_at 2022年1月18日 0018 11:01
 * @since 1.6.9
 */
public enum DeviceTypeEnum {

    PC("PC"),
    WX("wx"), // 微信小程序
    MOBILE("mobile");

    private String value;

    DeviceTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String deviceType) {
        return Arrays.stream(values())
                .filter(item -> deviceType.equals(item.getValue()))
                .findAny()
                .orElse(null) != null;
    }
}
