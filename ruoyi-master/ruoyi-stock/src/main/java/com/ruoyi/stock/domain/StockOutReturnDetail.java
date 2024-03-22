package com.ruoyi.stock.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库退货详情对象 stock_out_return_detail
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Data
public class StockOutReturnDetail extends BaseEntity {

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
     * 出库退货单号
     */
    @Excel(name = "出库退货单号")
    private String returnNo;

    /**
     * 行号
     */
    @Excel(name = "行号")
    private Integer lineNo;

    /**
     * 物料标签id
     */
    @Excel(name = "物料标签id")
    private Long labelId;

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
     * 退还数量
     */
    @Excel(name = "退还数量")
    private BigDecimal returnQuantity;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitCode;

    private String unitName;

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
     * 删除标识
     */
    private String delFlag;

}
