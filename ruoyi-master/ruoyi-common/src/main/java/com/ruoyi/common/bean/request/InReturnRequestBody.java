package com.ruoyi.common.bean.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 提交入库退货单
 */
@Data
public class InReturnRequestBody {

    /**
     * 入库退货单号
     */
    private String returnNo;

    /**
     * 退回标签
     */
    private Map<Long, BigDecimal> returnMap;

}
