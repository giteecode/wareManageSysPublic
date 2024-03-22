package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//调拨单
@Getter
public enum AllotProgressEnum {

    CREATED("created", "发起仓库创建"),
    PICKING( "picking","发起仓库拣货"),
    RECEIVE( "receive","目标仓库接收");

    private String value;
    private String label;

    AllotProgressEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        AllotProgressEnum[] enums = values();
        if(value != null){
            for (AllotProgressEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        AllotProgressEnum[] enums = values();
        for (AllotProgressEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

}
