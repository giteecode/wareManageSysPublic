package com.ruoyi.stock.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.stock.domain.StockOutOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 出库单Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockOutOrderMapper {

    /**
     * 查询出库单数量
     */
    public Map<String, Long> selectStockOutOrderTotal(@Param("selectDate") Date selectDate);

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
    public int insertStockOutOrder(StockOutOrder stockOutOrder);

    /**
     * 修改出库单
     *
     * @param stockOutOrder 出库单
     * @return 结果
     */
    public int updateStockOutOrder(StockOutOrder stockOutOrder);

    /**
     * 删除出库单
     *
     * @param orderId 出库单主键
     * @return 结果
     */
    public int deleteStockOutOrderByOrderId(Long orderId);

    /**
     * 批量删除出库单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockOutOrderByOrderIds(Long[] orderIds);
}
