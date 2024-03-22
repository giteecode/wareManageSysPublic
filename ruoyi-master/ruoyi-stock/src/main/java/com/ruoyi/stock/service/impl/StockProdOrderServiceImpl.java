package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockProdOrderMapper;
import com.ruoyi.stock.domain.StockProdOrder;
import com.ruoyi.stock.service.IStockProdOrderService;

/**
 * 生产订单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockProdOrderServiceImpl implements IStockProdOrderService {
    @Autowired
    private StockProdOrderMapper stockProdOrderMapper;

    /**
     * 查询生产订单
     *
     * @param orderId 生产订单主键
     * @return 生产订单
     */
    @Override
    public StockProdOrder selectStockProdOrderByOrderId(Long orderId) {
        return stockProdOrderMapper.selectStockProdOrderByOrderId(orderId);
    }

    /**
     * 查询生产订单
     *
     * @param orderNo
     * @return 生产订单
     */
    public StockProdOrder selectStockProdOrderByOrderNo(String orderNo){
        return stockProdOrderMapper.selectStockProdOrderByOrderNo(orderNo);
    }

    /**
     * 查询生产订单列表
     *
     * @param stockProdOrder 生产订单
     * @return 生产订单
     */
    @Override
    public List<StockProdOrder> selectStockProdOrderList(StockProdOrder stockProdOrder) {
        return stockProdOrderMapper.selectStockProdOrderList(stockProdOrder);
    }

    /**
     * 新增生产订单
     *
     * @param stockProdOrder 生产订单
     * @return 结果
     */
    @Override
    public int insertStockProdOrder(StockProdOrder stockProdOrder) {
        stockProdOrder.setCreateTime(DateUtils.getNowDate());
        return stockProdOrderMapper.insertStockProdOrder(stockProdOrder);
    }

    /**
     * 修改生产订单
     *
     * @param stockProdOrder 生产订单
     * @return 结果
     */
    @Override
    public int updateStockProdOrder(StockProdOrder stockProdOrder) {
        stockProdOrder.setUpdateTime(DateUtils.getNowDate());
        return stockProdOrderMapper.updateStockProdOrder(stockProdOrder);
    }

    /**
     * 批量删除生产订单
     *
     * @param orderIds 需要删除的生产订单主键
     * @return 结果
     */
    @Override
    public int deleteStockProdOrderByOrderIds(Long[] orderIds) {
        return stockProdOrderMapper.deleteStockProdOrderByOrderIds(orderIds);
    }

    /**
     * 删除生产订单信息
     *
     * @param orderId 生产订单主键
     * @return 结果
     */
    @Override
    public int deleteStockProdOrderByOrderId(Long orderId) {
        return stockProdOrderMapper.deleteStockProdOrderByOrderId(orderId);
    }
}
