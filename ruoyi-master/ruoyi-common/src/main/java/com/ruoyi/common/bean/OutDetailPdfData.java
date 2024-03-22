package com.ruoyi.common.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 出库单详情对象 stock_out_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class OutDetailPdfData {

    /**
     * 物料编码
     */
    private String matCode;

    /**
     * 物料名称
     */
    private String matName;

    /**
     * 图号
     */
    private String figNum;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 已领数量
     */
    private BigDecimal receivedQuantity;

    /**
     * 单位
     */
    private String unitName;

    /**
     * 货位
     */
    private String locationCode;

}
