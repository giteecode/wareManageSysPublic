package com.ruoyi.stock.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库单对象 stock_out_order
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockOutOrder extends BaseEntity {

    /**
     * 主键
     */
    private Long orderId;

    /**
     * 单据号
     */
    @Excel(name = "单据号")
    private String orderNo;

    /**
     * 单据类型
     */
    @Excel(name = "单据类型")
    private String orderType;

    private String orderTypeLabel;

    /**
     * 生产订单号
     */
    @Excel(name = "生产订单号")
    private String prodOrderNo;

    /**
     * 仓库
     */
    @Excel(name = "仓库")
    private String warehouseCode;

    private String warehouseName;

    /**
     * 车间
     */
    @Excel(name = "车间")
    private String workshopCode;

    private String workshopName;

    /**
     * 原因
     */
    @Excel(name = "原因")
    private String orderReason;

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
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 单据状态
     */
    @Excel(name = "单据状态")
    private String orderStatus;

    private String orderStatusLabel;

    /**
     * 库管员
     */
    @Excel(name = "库管员")
    private String warehouseKeeper;

    /**
     * 删除标识
     */
    private String delFlag;

    private List<StockOutDetail> detailList;

}
