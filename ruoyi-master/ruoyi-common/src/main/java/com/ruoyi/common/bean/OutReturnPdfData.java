package com.ruoyi.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * 出库单退货对象 stock_out_return
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class OutReturnPdfData {

    /**
     * 出库退货单号
     */
    private String returnNo;

    /**
     * 仓库
     */
    private String warehouseCode;

    private String warehouseName;

    /**
     * 车间
     */
    private String workshopCode;

    private String workshopName;

    /**
     * 退货类型
     */
    private String returnType;

    private String returnTypeLabel;

    /**
     * 退货原因
     */
    private String returnReason;

    /**
     * 退货状态
     */
    private String returnStatus;

    private String returnStatusLabel;

    /**
     * 生产订单号
     */
    private String prodOrderNo;

    /**
     * 出库单号
     */
    private String orderNo;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
