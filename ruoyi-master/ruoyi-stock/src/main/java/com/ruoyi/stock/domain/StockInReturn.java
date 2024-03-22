package com.ruoyi.stock.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 入库单退货对象 stock_in_return
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockInReturn extends BaseEntity {

    /**
     * 主键
     */
    private Long returnId;

    /**
     * 入库退货单号
     */
    @Excel(name = "入库退货单号")
    private String returnNo;

    /**
     * 入库退货类型
     */
    @Excel(name = "入库单号")
    private String returnType;

    private String returnTypeLabel;

    /**
     * 入库退货原因
     */
    @Excel(name = "入库退货原因")
    private String returnReason;

    /**
     * 退货单据状态
     */
    @Excel(name = "退货单据状态")
    private String returnStatus;

    private String returnStatusLabel;

    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String orderNo;

    /**
     * 仓库
     */
    @Excel(name = "仓库")
    private String warehouseCode;

    private String warehouseName;

    /**
     * 删除标识
     */
    private String delFlag;

    private List<StockInReturnDetail> detailList;

}
