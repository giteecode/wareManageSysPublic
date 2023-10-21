package com.education.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;

/**
 * 试题批改状态枚举
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CorrectStatusEnum {
    ERROR(0, "错误"),
    RIGHT(1, "正确"),
    CORRECT_RUNNING(2, "待批改"),
    CORRECTED(3, "已批改");

    private Integer value;
    private String name;
    CorrectStatusEnum(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
            return name;
    }


    public static CorrectStatusEnum getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(item -> item.value.equals(code))
                .findAny().orElse(null);
    }
}