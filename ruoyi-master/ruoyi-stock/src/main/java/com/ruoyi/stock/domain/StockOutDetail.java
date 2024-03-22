package com.ruoyi.stock.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库单详情对象 stock_out_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockOutDetail extends BaseEntity {

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
     * 车间
     */
    @Excel(name = "车间")
    private String workshopCode;

    /**
     * 货位
     */
    @Excel(name = "货位")
    private String locationCode;

    /**
     * 生产订单号
     */
    @Excel(name = "生产订单号")
    private String prodOrderNo;

    /**
     * 出库单号
     */
    @Excel(name = "出库单号")
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
     * 已领数量
     */
    @Excel(name = "已领数量")
    private BigDecimal receivedQuantity;

    //已扫码数量
    private BigDecimal scannedQuantity;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitCode;

    private String unitName;

    /**
     * 删除标识
     */
    private String delFlag;

}
