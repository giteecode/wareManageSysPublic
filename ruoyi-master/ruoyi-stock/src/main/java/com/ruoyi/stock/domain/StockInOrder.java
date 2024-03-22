package com.ruoyi.stock.domain;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 入库单对象 stock_in_order
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockInOrder extends BaseEntity {

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
     * 单据状态
     */
    @Excel(name = "单据状态")
    private String orderStatus;

    private String orderStatusLabel;

    /**
     * 检验状态
     */
    @Excel(name = "检验状态")
    private String checkStatus;

    private String checkStatusLabel;

    /**
     * 检验员
     */
    @Excel(name = "检验员")
    private String checkBy;

    /**
     * 删除标识
     */
    private String delFlag;

    //入库单详情
    private List<StockInDetail> detailList;

}
