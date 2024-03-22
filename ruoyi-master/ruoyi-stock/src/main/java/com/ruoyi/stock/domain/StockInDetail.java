package com.ruoyi.stock.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单详情对象 stock_in_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockInDetail extends BaseEntity {

    /**
     * 主键
     */
    private Long detailId;

    /**
     * 仓库
     */
    @Excel(name = "仓库")
    private String warehouseCode;

    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String orderNo;

    /**
     * 行号
     */
    @Excel(name = "行号")
    private Integer lineNo;

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
     * 单位
     */
    @Excel(name = "单位")
    private String unitCode;

    private String unitName;

    /**
     * 物料标签id
     */
    @Excel(name = "物料标签id")
    private Long labelId;

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
     * 合格数量
     */
    @Excel(name = "合格数量")
    private BigDecimal qualifiedQuantity;

    /**
     * 入库数量
     */
    @Excel(name = "入库数量")
    private BigDecimal stockInQuantity;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

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
     * 删除标识
     */
    private String delFlag;

    private String locationCode;

}
