package com.ruoyi.common.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库单对象 stock_out_order
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class OutOrderPdfData {

    /**
     * 单据号
     */
    private String orderNo;

    /**
     * 单据类型
     */
    private String orderType;

    /**
     * 原因
     */
    private String orderReason;

    /**
     * 仓库
     */
    private String warehouseName;

    /**
     * 车间
     */
    private String workshopName;

    /**
     * 库管员
     */
    private String warehouseKeeper;

    /**
     * 生产订单号
     */
    private String prodOrderNo;

    /**
     * 物料编码
     */
    private String matCode;

    /**
     * 物料名称
     */
    private String matName;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
