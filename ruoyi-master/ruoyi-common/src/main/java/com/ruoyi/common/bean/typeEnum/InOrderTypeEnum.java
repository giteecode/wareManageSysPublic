package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//入库单类型
@Getter
public enum InOrderTypeEnum {

    PURCHASE("purchase", "采购入库"),
    OUTSOURCING("outsourcing","委外入库"),
    PRODUCTION("production", "生产入库");

    private String value;
    private String label;

    InOrderTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        InOrderTypeEnum[] enums = values();
        if(value != null){
            for (InOrderTypeEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        InOrderTypeEnum[] enums = values();
        for (InOrderTypeEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

}
