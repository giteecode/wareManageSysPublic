package com.ruoyi.common.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 入库单详情对象 stock_in_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class InDetailPdfData {

    /**
     * 物料编码
     */
    private String matCode;

    /**
     * 物料名称
     */
    private String matName;

    /**
     * 财务编码
     */
    private String fdCode;

    /**
     * 图号
     */
    private String figNum;

    /**
     * 单位
     */
    private String unitName;

    /**
     * 批次
     */
    private String batch;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 供应商名称
     */
    private String supplierName;

}
