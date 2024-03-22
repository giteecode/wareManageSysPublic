package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//入库单退货类型
@Getter
public enum InOrderReturnTypeEnum {

    /**
     * 采购入库退货
     */
    PURCHASE_RETURN("purchase_return", "采购入库退货");

    private String value;
    private String label;

    InOrderReturnTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        InOrderReturnTypeEnum[] enums = values();
        if(value != null){
            for (InOrderReturnTypeEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        InOrderReturnTypeEnum[] enums = values();
        for (InOrderReturnTypeEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

}
