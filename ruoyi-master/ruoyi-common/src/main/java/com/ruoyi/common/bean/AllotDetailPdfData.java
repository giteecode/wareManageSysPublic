package com.ruoyi.common.bean;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 调拨单详情对象 stock_allot_detail
 *
 * @author ruoyi
 * @date 2022-08-05
 */
@Data
public class AllotDetailPdfData {

    /**
     * 主键
     */
    private Long detailId;

    /**
     * 调拨单号
     */
    private String allotNo;

    /**
     * 发起仓库
     */
    private String srcWarehouseCode;

    /**
     * 发起货位
     */
    private String srcLocationCode;

    /**
     * 目标仓库
     */
    private String destWarehouseCode;

    /**
     * 目标货位
     */
    private String destLocationCode;

    /**
     * 行号
     */
    private Integer lineNo;

    /**
     * 物料标签id
     */
    private Long labelId;

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
     * 批次
     */
    private String batch;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 签收数量
     */
    private BigDecimal signQuantity;

    /**
     * 单位
     */
    private String unitCode;

    private String unitName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

}
