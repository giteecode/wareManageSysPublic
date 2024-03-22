package com.ruoyi.stock.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.Arith;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料标签对象 stock_mat_label
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockMatLabel extends BaseEntity {

    /**
     * 主键
     */
    private Long labelId;

    /**
     * 仓库
     */
    @Excel(name = "仓库")
    private String warehouseCode;

    /**
     * 货位
     */
    @Excel(name = "货位")
    private String locationCode;

    /**
     * 标签类型
     */
    @Excel(name = "标签类型")
    private String labelType;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String matCode;

    /**
     * 物料描述
     */
    @Excel(name = "物料描述")
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

    private String matGroupName;

    /**
     * 分类
     */
    @Excel(name = "分类")
    private String matClass;

    private String matClassName;

    /**
     * 基本单位
     */
    @Excel(name = "基本单位")
    private String unitCode;

    private String unitName;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private String batch;

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
     * 生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date prodTime;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 可用数量
     */
    @Excel(name = "可用数量")
    private BigDecimal usableQuantity;

    /**
     * 已领取数量
     */
    @Excel(name = "已领取数量")
    private BigDecimal receivedQuantity;

    //剩余数量
    private BigDecimal remainingQuantity;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String orderNo;

    /**
     * 入库单类型
     */
    @Excel(name = "入库单类型")
    private String orderType;

    private String orderTypeLabel;

    /**
     * 删除标识
     */
    private String delFlag;

}
