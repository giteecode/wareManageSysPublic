package com.ruoyi.stock.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生产订单对象 stock_prod_order
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockProdOrder extends BaseEntity {

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
     * 工令号
     */
    @Excel(name = "工令号")
    private String workNo;

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
     * 车间
     */
    @Excel(name = "车间")
    private String workshopCode;

    private String workshopName;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String orderStatus;

    private String orderStatusLabel;

    /**
     * 删除标识
     */
    private String delFlag;

}
