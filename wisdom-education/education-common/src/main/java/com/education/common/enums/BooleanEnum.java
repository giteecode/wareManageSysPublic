package com.education.common.enums;

/**
 * @author zengjintao
 * @create_at 2021/12/26 11:10
 * @since version 1.0.4
 */
public enum  BooleanEnum {

    YES(1, true, "是"),
    NO(0, false, "否");


    private Integer code;
    private boolean flag;
    private String value;

    BooleanEnum(Integer code, boolean flag, String value) {
        this.value = value;
        this.flag = flag;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isFlag() {
        return flag;
    }

    public String getValue() {
        return value;
    }
}
