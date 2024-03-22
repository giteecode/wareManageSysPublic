package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//出库单退货类型
@Getter
public enum OutOrderReturnTypeEnum {

    PRODUCTION_RETURN("production_return", "生产出库退货"),
    REPAIR_RETURN("repair_return", "补领出库退货"),
    COMMON_RETURN("common_return", "通用出库退货");

    private String value;
    private String label;

    OutOrderReturnTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        OutOrderReturnTypeEnum[] enums = values();
        if(value != null){
            for (OutOrderReturnTypeEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        OutOrderReturnTypeEnum[] enums = values();
        for (OutOrderReturnTypeEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

}
