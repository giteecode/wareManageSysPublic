package com.ruoyi.common.utils;

import com.ruoyi.common.bean.typeEnum.InOrderReturnTypeEnum;
import com.ruoyi.common.bean.typeEnum.InOrderTypeEnum;
import com.ruoyi.common.bean.typeEnum.OutOrderReturnTypeEnum;
import com.ruoyi.common.bean.typeEnum.OutOrderTypeEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//订单号生成工具类
public class OrderNoUtil {

    /**
     * 采购入库前缀
     */
    public static final String IN_PURCHASE_PREFIX = "IP";

    /**
     * 生产订单前缀
     */
    public static final String PROD_PREFIX = "P";
    /**
     * 生产领料前缀
     */
    public static final String OUT_PROD_PREFIX = "OP";
    /**
     * 补领料前缀
     */
    public static final String OUT_REPAIR_PREFIX = "OR";
    /**
     * 通用领料前缀
     */
    public static final String OUT_COMMON_PREFIX = "OC";

    /**
     * 采购入库退货前缀
     */
    public static final String IN_PURCHASE_RETURN_PREFIX = "IPR";

    /**
     * 生产领料退货前缀
     */
    public static final String OUT_PROD_RETURN_PREFIX = "OPR";
    /**
     * 补领料退货前缀
     */
    public static final String OUT_REPAIR_RETURN_PREFIX = "ORR";
    /**
     * 通用领料退货
     */
    public static final String OUT_COMMON_RETURN_PREFIX = "OCR";
    /**
     * 调拨单
     */
    public static final String ALLOT_PREFIX = "A";

    public static synchronized String generateUniqueKey(String prefix){
        Integer r = (int)((Math.random() * 9 + 1) * 10);
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String timeStr = sdf.format(new Date());
        return prefix + timeStr + r;
    }

    public static String getInOrderNo(String orderType){
        String prefix = "";
        if(InOrderTypeEnum.PURCHASE.getValue().equals(orderType)){
            prefix = IN_PURCHASE_PREFIX;
        }
        return generateUniqueKey(prefix);
    }

    public static String getInOrderReturnNo(String returnType){
        String prefix = "";
        if(InOrderReturnTypeEnum.PURCHASE_RETURN.getValue().equals(returnType)){
            prefix = IN_PURCHASE_RETURN_PREFIX;
        }
        return generateUniqueKey(prefix);
    }

    public static String getOutOrderNo(String orderType){
        String prefix = "";
        if(OutOrderTypeEnum.PRODUCTION.getValue().equals(orderType)){
            prefix = OUT_PROD_PREFIX;
        }else if(OutOrderTypeEnum.REPAIR.getValue().equals(orderType)){
            prefix = OUT_REPAIR_PREFIX;
        }else if(OutOrderTypeEnum.COMMON.getValue().equals(orderType)){
            prefix = OUT_COMMON_PREFIX;
        }
        return generateUniqueKey(prefix);
    }

    public static String getOutOrderReturnNo(String returnType){
        String prefix = "";
        if(OutOrderReturnTypeEnum.PRODUCTION_RETURN.getValue().equals(returnType)){
            prefix = OUT_PROD_RETURN_PREFIX;
        }else if(OutOrderReturnTypeEnum.REPAIR_RETURN.getValue().equals(returnType)){
            prefix = OUT_REPAIR_RETURN_PREFIX;
        }else if(OutOrderReturnTypeEnum.COMMON_RETURN.getValue().equals(returnType)){
            prefix = OUT_COMMON_RETURN_PREFIX;
        }
        return generateUniqueKey(prefix);
    }

}
