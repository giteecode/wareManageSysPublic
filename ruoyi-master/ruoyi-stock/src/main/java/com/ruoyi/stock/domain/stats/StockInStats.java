package com.ruoyi.stock.domain.stats;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 入库统计
 */
@Data
public class StockInStats {

    @Excel(name = "物料编码")
    private String matCode;

    @Excel(name = "物料名称")
    private String matName;

    @Excel(name = "图号")
    private String figNum;

    @Excel(name = "入库单物料数")
    private BigDecimal quantity;//入库单物料数

    @Excel(name = "物料合格数")
    private BigDecimal qualifiedQuantity;//物料合格数

    @Excel(name = "实际入库数")
    private BigDecimal stockInQuantity;//实际入库数

    @Excel(name = "退货数")
    private BigDecimal returnQuantity;//退货数

    @Excel(name = "实际退货数")
    private BigDecimal finishReturnQuantity;//实际退货数

}
