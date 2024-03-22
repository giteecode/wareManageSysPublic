package com.ruoyi.common.bean.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物料标签对象 stock_mat_label
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class MatLabelRequestData {

    /**
     * 主键
     */
    private Long labelId;

    /**
     * 仓库
     */
    private String warehouseCode;

    /**
     * 货位
     */
    private String locationCode;

    /**
     * 标签类型
     */
    private String labelType;

    /**
     * 物料编码
     */
    private String matCode;

    /**
     * 物料描述
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
     * 物料组
     */
    private String matGroup;

    private String matGroupName;

    /**
     * 分类
     */
    private String matClass;

    private String matClassName;

    /**
     * 基本单位
     */
    private String unitCode;

    private String unitName;

    /**
     * 批次
     */
    private String batch;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 生产时间
     */
    private Date prodTime;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 可用数量
     */
    private BigDecimal usableQuantity;

    /**
     * 已领取数量
     */
    private BigDecimal receivedQuantity;

    //剩余数量
    private BigDecimal remainingQuantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 入库单号
     */
    private String orderNo;

    /**
     * 入库单类型
     */
    private String orderType;

    /**
     * 删除标识
     */
    private String delFlag;

}
