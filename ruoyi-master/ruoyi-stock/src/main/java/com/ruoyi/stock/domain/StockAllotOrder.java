package com.ruoyi.stock.domain;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 调拨单对象 stock_allot_order
 *
 * @author ruoyi
 * @date 2022-08-05
 */
@Data
public class StockAllotOrder extends BaseEntity {

    /**
     * 主键
     */
    private Long allotId;

    /**
     * 调拨单号
     */
    @Excel(name = "调拨单号")
    private String allotNo;

    /**
     * 调拨原因
     */
    @Excel(name = "调拨原因")
    private String allotReason;

    /**
     * 发起仓库
     */
    @Excel(name = "发起仓库")
    private String srcWarehouseCode;

    private String srcWarehouseName;

    /**
     * 目标仓库
     */
    @Excel(name = "目标仓库")
    private String destWarehouseCode;

    private String destWarehouseName;

    /**
     * 调拨单状态
     */
    @Excel(name = "调拨单状态")
    private String allotStatus;

    private String allotStatusLabel;

    /**
     * 调拨进度
     */
    @Excel(name = "调拨进度")
    private String allotProgress;

    private String allotProgressLabel;

    /**
     * 删除标识
     */
    private String delFlag;

    private List<StockAllotDetail> detailList;

}
