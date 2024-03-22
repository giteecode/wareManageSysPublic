package com.ruoyi.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * 入库单退货对象 stock_in_return
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class InReturnPdfData {

    /**
     * 入库退货单号
     */
    private String returnNo;

    /**
     * 入库退货类型
     */
    private String returnType;

    /**
     * 入库退货原因
     */
    private String returnReason;

    /**
     * 入库单号
     */
    private String orderNo;

    private String warehouseName;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
