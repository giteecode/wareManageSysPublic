package com.ruoyi.stock.domain;

import java.math.BigDecimal;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 调拨单详情对象 stock_allot_detail
 *
 * @author ruoyi
 * @date 2022-08-05
 */
@Data
public class StockAllotDetail extends BaseEntity {

    /**
     * 主键
     */
    private Long detailId;

    /**
     * 调拨单号
     */
    @Excel(name = "调拨单号")
    private String allotNo;

    /**
     * 发起仓库
     */
    @Excel(name = "发起仓库")
    private String srcWarehouseCode;

    /**
     * 发起货位
     */
    @Excel(name = "发起货位")
    private String srcLocationCode;

    /**
     * 目标仓库
     */
    @Excel(name = "目标仓库")
    private String destWarehouseCode;

    /**
     * 目标货位
     */
    @Excel(name = "目标货位")
    private String destLocationCode;

    /**
     * 行号
     */
    @Excel(name = "行号")
    private Integer lineNo;

    /**
     * 物料标签id
     */
    @Excel(name = "物料标签id")
    private Long labelId;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String matCode;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String matName;

    /**
     * 财务编码
     */
    @Excel(name = "财务编码")
    private String fdCode;

    /**
     * 图号
     */
    @Excel(name = "图号")
    private String figNum;

    /**
     * 物料组
     */
    @Excel(name = "物料组")
    private String matGroup;

    /**
     * 物料分类
     */
    @Excel(name = "物料分类")
    private String matClass;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String batch;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 签收数量
     */
    @Excel(name = "签收数量")
    private BigDecimal signQuantity;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitCode;

    private String unitName;

    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /**
     * 删除标识
     */
    private String delFlag;

}
