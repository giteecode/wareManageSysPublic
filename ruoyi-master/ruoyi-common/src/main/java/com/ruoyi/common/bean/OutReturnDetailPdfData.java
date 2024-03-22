package com.ruoyi.common.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库退货详情对象 stock_out_return_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class OutReturnDetailPdfData {

    /**
     * 仓库
     */
    private String warehouseCode;

    /**
     * 车间
     */
    private String workshopCode;

    /**
     * 货位
     */
    private String locationCode;

    /**
     * 出库退货单号
     */
    private String returnNo;

    /**
     * 行号
     */
    private Integer lineNo;

    /**
     * 物料标签id
     */
    private String labelId;

    /**
     * 物料编码
     */
    private String matCode;

    /**
     * 物料名称
     */
    private String matName;

    /**
     * 财务编码
     */
    private String fdCode;

    /**
     * 图号
     */
    private String figNum;

    /**
     * 批次
     */
    private String batch;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 退还数量
     */
    private BigDecimal returnQuantity;

    /**
     * 单位
     */
    private String unitCode;

    private String unitName;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
