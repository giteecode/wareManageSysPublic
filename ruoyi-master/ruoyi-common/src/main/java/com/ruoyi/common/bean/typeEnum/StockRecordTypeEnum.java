package com.ruoyi.common.bean.typeEnum;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StockRecordTypeEnum {
    // 入库
    IN_PURCHASE("in_purchase", "采购入库"),
    IN_OUTSOURCING("in_outsourcing","委外入库"),
    IN_PRODUCTION("in_production", "生产入库"),
    // 出库
    OUT_PRODUCTION("out_production", "生产出库"),
    OUT_REPAIR("out_repair", "补领出库"),
    OUT_COMMON("out_common", "通用出库"),
    // 退货
    IN_PURCHASE_RETURN("in_purchase_return", "采购入库退货"),
    OUT_PRODUCTION_RETURN("out_production_return", "生产出库退货"),
    OUT_REPAIR_RETURN("out_repair_return", "补领出库退货"),
    OUT_COMMON_RETURN("out_common_return", "通用出库退货"),
    // 上下架
    UPPER("upper", "上架"),
    LOWER("lower", "下架"),
    //调拨
    ALLOT_IN("allot_in", "调拨入库"),
    ALLOT_OUT("allot_out", "调拨出库");

    private String value;
    private String label;

    StockRecordTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static String getLabel(String value) {
        StockRecordTypeEnum[] enums = values();
        if(value != null){
            for (StockRecordTypeEnum e : enums) {
                if (e.getValue().equals(value)) {
                    return e.getLabel();
                }
            }
        }
        return null;
    }

    public static Map<String, String> toMap(){
        Map<String, String> m = new HashMap<>();
        StockRecordTypeEnum[] enums = values();
        for (StockRecordTypeEnum e : enums) {
            m.put(e.getValue(), e.getLabel());
        }
        return m;
    }

    /**
     * 获取入库类型
     */
    public static String getStockInRecordType(String orderType){
        String recordType = "";
        if(InOrderTypeEnum.PURCHASE.getValue().equals(orderType)){
            recordType = StockRecordTypeEnum.IN_PURCHASE.getValue();
        }else if(InOrderTypeEnum.OUTSOURCING.getValue().equals(orderType)){
            recordType = StockRecordTypeEnum.IN_OUTSOURCING.getValue();
        }else if(InOrderTypeEnum.PRODUCTION.getValue().equals(orderType)){
            recordType = StockRecordTypeEnum.IN_PRODUCTION.getValue();
        }
        return recordType;
    }

    /**
     * 获取入库退货类型
     */
    public static String getStockInReturnRecordType(String returnType){
        String recordType = "";
        if(InOrderReturnTypeEnum.PURCHASE_RETURN.getValue().equals(returnType)){
            recordType = StockRecordTypeEnum.IN_PURCHASE_RETURN.getValue();
        }
        return recordType;
    }

    /**
     * 获取出库类型
     */
    public static String getStockOutRecordType(String orderType){
        String recordType = "";
        if(OutOrderTypeEnum.PRODUCTION.getValue().equals(orderType)){
            recordType = StockRecordTypeEnum.OUT_PRODUCTION.getValue();
        }else if(OutOrderTypeEnum.REPAIR.getValue().equals(orderType)){
            recordType = StockRecordTypeEnum.OUT_REPAIR.getValue();
        }else if(OutOrderTypeEnum.COMMON.getValue().equals(orderType)){
            recordType = StockRecordTypeEnum.OUT_COMMON.getValue();
        }
        return recordType;
    }

    /**
     * 获取出库退货类型
     */
    public static String getStockOutReturnRecordType(String returnType){
        String recordType = "";
        if(OutOrderReturnTypeEnum.PRODUCTION_RETURN.getValue().equals(returnType)){
            recordType = StockRecordTypeEnum.OUT_PRODUCTION_RETURN.getValue();
        }else if(OutOrderReturnTypeEnum.REPAIR_RETURN.getValue().equals(returnType)){
            recordType = StockRecordTypeEnum.OUT_REPAIR_RETURN.getValue();
        }else if(OutOrderReturnTypeEnum.COMMON_RETURN.getValue().equals(returnType)){
            recordType = StockRecordTypeEnum.OUT_COMMON_RETURN.getValue();
        }
        return recordType;
    }

}
