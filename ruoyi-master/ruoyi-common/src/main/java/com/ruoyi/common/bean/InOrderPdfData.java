package com.ruoyi.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * 入库单对象 stock_in_order
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class InOrderPdfData {

    /**
     * 单据号
     */
    private String orderNo;

    /**
     * 单据类型
     */
    private String orderType;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
