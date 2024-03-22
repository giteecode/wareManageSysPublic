package com.ruoyi.stock.domain.stats;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockInfoSummary {

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

    //总计数量
    @Excel(name = "数量")
    private BigDecimal statsQuantity;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unitCode;

}
