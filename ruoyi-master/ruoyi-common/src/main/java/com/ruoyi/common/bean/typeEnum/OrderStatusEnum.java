package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//单据状态
@Getter
public enum OrderStatusEnum {

    CREATED("created", "已创建"),
    PRINTED( "printed","已打印"),
    ENTERED( "entered","已入库"),
    RECEIVED( "received","已领料"),
    RETURNED("returned", "已退料");

    private String value;
    private String label;

    OrderStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        OrderStatusEnum[] enums = values();
        if(value != null){
            for (OrderStatusEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        OrderStatusEnum[] enums = values();
        for (OrderStatusEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

}
