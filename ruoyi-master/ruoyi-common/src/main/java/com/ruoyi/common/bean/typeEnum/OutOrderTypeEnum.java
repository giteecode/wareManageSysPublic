package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//出库单类型
@Getter
public enum OutOrderTypeEnum {

    PRODUCTION("production", "生产出库"),
    REPAIR("repair", "补领出库"),
    COMMON("common", "通用出库");

    private String value;
    private String label;

    OutOrderTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        OutOrderTypeEnum[] enums = values();
        if(value != null){
            for (OutOrderTypeEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        OutOrderTypeEnum[] enums = values();
        for (OutOrderTypeEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

}
