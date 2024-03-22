package com.ruoyi.stock.mapper;

import java.util.List;
import com.ruoyi.stock.domain.StockAllotOrder;

/**
 * 调拨单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface StockAllotOrderMapper 
{
    /**
     * 查询调拨单
     * 
     * @param allotId 调拨单主键
     * @return 调拨单
     */
    public StockAllotOrder selectStockAllotOrderByAllotId(Long allotId);

    /**
     * 查询调拨单
     */
    public StockAllotOrder selectStockAllotOrderByAllotNo(String allotNo);

    /**
     * 查询调拨单列表
     * 
     * @param stockAllotOrder 调拨单
     * @return 调拨单集合
     */
    public List<StockAllotOrder> selectStockAllotOrderList(StockAllotOrder stockAllotOrder);

    /**
     * 新增调拨单
     * 
     * @param stockAllotOrder 调拨单
     * @return 结果
     */
    public int insertStockAllotOrder(StockAllotOrder stockAllotOrder);

    /**
     * 修改调拨单
     * 
     * @param stockAllotOrder 调拨单
     * @return 结果
     */
    public int updateStockAllotOrder(StockAllotOrder stockAllotOrder);

    /**
     * 删除调拨单
     * 
     * @param allotId 调拨单主键
     * @return 结果
     */
    public int deleteStockAllotOrderByAllotId(Long allotId);

    /**
     * 批量删除调拨单
     * 
     * @param allotIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockAllotOrderByAllotIds(Long[] allotIds);
}
