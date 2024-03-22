package com.ruoyi.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.bean.request.StockOutRequestBody;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.domain.StockOutOrder;
import com.ruoyi.stock.domain.stats.StockOutStats;

/**
 * 出库单Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockOutOrderService {

    /**
     * 查询出库单数量
     */
    public Map<String, Long> selectStockOutOrderTotal(Date selectDate);

    /**
     * 查询出库单
     *
     * @param orderId 出库单主键
     * @return 出库单
     */
    public StockOutOrder selectStockOutOrderByOrderId(Long orderId);

    /**
     * 查询出库单
     *
     * @param orderNo
     * @return 出库单
     */
    public StockOutOrder selectStockOutOrderByOrderNo(String orderNo);

    /**
     * 查询出库单列表
     *
     * @param stockOutOrder 出库单
     * @return 出库单集合
     */
    public List<StockOutOrder> selectStockOutOrderList(StockOutOrder stockOutOrder);

    /**
     * 新增出库单
     *
     * @param stockOutOrder 出库单
     * @return 结果
     */
    public int insertStockOutOrder(String username, StockOutOrder stockOutOrder);

    /**
     * 修改出库单
     *
     * @param stockOutOrder 出库单
     * @return 结果
     */
    public int updateStockOutOrder(StockOutOrder stockOutOrder);

    /**
     * 批量删除出库单
     *
     * @param orderIds 需要删除的出库单主键集合
     * @return 结果
     */
    public int deleteStockOutOrderByOrderIds(Long[] orderIds);

    /**
     * 删除出库单信息
     *
     * @param orderId 出库单主键
     * @return 结果
     */
    public int deleteStockOutOrderByOrderId(Long orderId);

    /**
     * 扫码提交出库单-出库
     */
    public AjaxResult submitStockOut(String username, StockOutRequestBody stockOutRequestBody);

    /**
     * 出库统计
     */
    public List<StockOutStats> selectStockOutStatsList(String matCode, String matName);

}
