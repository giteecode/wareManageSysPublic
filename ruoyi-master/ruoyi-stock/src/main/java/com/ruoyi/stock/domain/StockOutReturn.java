package com.ruoyi.stock.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 出库单退货对象 stock_out_return
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockOutReturn extends BaseEntity {

    /**
     * 主键
     */
    private Long returnId;

    /**
     * 出库退货单号
     */
    @Excel(name = "出库退货单号")
    private String returnNo;

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
     * 退货类型
     */
    @Excel(name = "退货类型")
    private String returnType;

    private String returnTypeLabel;

    /**
     * 退货原因
     */
    @Excel(name = "退货原因")
    private String returnReason;

    /**
     * 退货状态
     */
    @Excel(name = "退货状态")
    private String returnStatus;

    private String returnStatusLabel;

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
     * 删除标识
     */
    private String delFlag;

    private List<StockOutReturnDetail> detailList;

}
