package com.ruoyi.common.bean.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 提交出库单
 */
@Data
public class StockOutRequestBody {

    /**
     * 出库单号
     */
    private String orderNo;

    /**
     * 领取标签
     */
    private Map<Long, BigDecimal> receivedMap;

}
