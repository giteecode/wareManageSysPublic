package com.ruoyi.stock.mapper;

import java.util.List;

import com.ruoyi.stock.domain.StockProdOrder;

/**
 * 生产订单Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockProdOrderMapper {
    /**
     * 查询生产订单
     *
     * @param orderId 生产订单主键
     * @return 生产订单
     */
    public StockProdOrder selectStockProdOrderByOrderId(Long orderId);

    /**
     * 查询生产订单
     *
     * @param orderNo
     * @return 生产订单
     */
    public StockProdOrder selectStockProdOrderByOrderNo(String orderNo);

    /**
     * 查询生产订单列表
     *
     * @param stockProdOrder 生产订单
     * @return 生产订单集合
     */
    public List<StockProdOrder> selectStockProdOrderList(StockProdOrder stockProdOrder);

    /**
     * 新增生产订单
     *
     * @param stockProdOrder 生产订单
     * @return 结果
     */
    public int insertStockProdOrder(StockProdOrder stockProdOrder);

    /**
     * 修改生产订单
     *
     * @param stockProdOrder 生产订单
     * @return 结果
     */
    public int updateStockProdOrder(StockProdOrder stockProdOrder);

    /**
     * 删除生产订单
     *
     * @param orderId 生产订单主键
     * @return 结果
     */
    public int deleteStockProdOrderByOrderId(Long orderId);

    /**
     * 批量删除生产订单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockProdOrderByOrderIds(Long[] orderIds);
}
