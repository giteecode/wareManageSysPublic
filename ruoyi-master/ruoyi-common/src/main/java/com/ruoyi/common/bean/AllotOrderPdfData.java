package com.ruoyi.common.bean;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 调拨单对象 stock_allot_order
 *
 * @author ruoyi
 * @date 2022-08-05
 */
@Data
public class AllotOrderPdfData {

    /**
     * 主键
     */
    private Long allotId;

    /**
     * 调拨单号
     */
    private String allotNo;

    /**
     * 调拨原因
     */
    private String allotReason;

    /**
     * 发起仓库
     */
    private String srcWarehouseCode;

    private String srcWarehouseName;

    /**
     * 目标仓库
     */
    private String destWarehouseCode;

    private String destWarehouseName;

    /**
     * 调拨单状态
     */
    private String allotStatus;

    private String allotStatusLabel;

    /**
     * 调拨进度
     */
    private String allotProgress;

    private String allotProgressLabel;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
