package com.ruoyi.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库单退货详情对象 stock_in_return_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class InReturnDetailPdfData {

    /**
     * 行号
     */
    private Integer lineNo;

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
     * 物料组
     */
    private String matGroup;

    /**
     * 物料分类
     */
    private String matClass;

    /**
     * 单位
     */
    private String unitCode;

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
     * 退还数量
     */
    private BigDecimal returnQuantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 货位
     */
    private String locationCode;

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

}
