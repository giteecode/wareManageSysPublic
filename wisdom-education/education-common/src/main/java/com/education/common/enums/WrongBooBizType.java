package com.education.common.enums;

/**
 * @author zjt
 * @create_at 2022年7月26日 0026 10:32
 * @since 1.0.5
 */
public enum WrongBooBizType {

    PAPER_QUESTION(1, "试卷错题");

    private Integer value;
    private String name;

    WrongBooBizType(Integer value, String name) {
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
