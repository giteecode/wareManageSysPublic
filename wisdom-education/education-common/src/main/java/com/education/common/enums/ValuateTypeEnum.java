package com.education.common.enums;

/**
 * @author zengjintao
 * @create_at 2021/10/17 9:38
 * @since version 1.0.3
 */
public enum ValuateTypeEnum {

    GOOD(1, "好评"), NEUTRAL(2, "中评"), NEGATIVE (3, "差评");
    private Integer value;
    private String name;

    ValuateTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
