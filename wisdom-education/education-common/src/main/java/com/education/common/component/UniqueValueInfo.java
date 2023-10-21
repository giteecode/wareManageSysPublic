package com.education.common.component;

import java.lang.reflect.Field;

public class UniqueValueInfo {

    private Field field;
    private String uniqueValue; // 注解值

    public UniqueValueInfo(Field field, String uniqueValue) {
        this.field = field;
        this.uniqueValue = uniqueValue;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }

    public Field getField() {
        return field;
    }

    public String getUniqueValue() {
        return uniqueValue;
    }
}
