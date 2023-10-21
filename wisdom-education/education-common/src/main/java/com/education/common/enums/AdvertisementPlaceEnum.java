package com.education.common.enums;

import java.util.Arrays;

/**
 * 广告位置枚举
 * @author zjt
 * @create_at 2022年3月25日 0025 13:35
 * @since 1.0.5
 */
public enum AdvertisementPlaceEnum {

    WEB_STUDENT_INDEX(1, 3,"学生端首页"),
    MOBILE_STUDENT_INDEX(2, 3,"手机端学生端首页"),

    STUDENT_LOGO(3, 3,"学生端首页LOGO"),
    ;

    private Integer code;
    private String name;
    private Integer maxNumber;

    AdvertisementPlaceEnum(Integer code, Integer maxNumber, String name) {
        this.code = code;
        this.maxNumber = maxNumber;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public static AdvertisementPlaceEnum getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(code)).findAny()
                .orElse(null);
    }
}
