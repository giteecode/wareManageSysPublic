package com.ruoyi.stock.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.stock.domain.StockInOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 入库单Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface StockInOrderMapper {

    /**
     * 查询入库单数量
     */
    public Map<String, Long> selectStockInOrderTotal(@Param("selectDate") Date selectDate);

    /**
     * 查询入库单
     *
     * @param orderId 入库单主键
     * @return 入库单
     */
    public StockInOrder selectStockInOrderByOrderId(Long orderId);

    /**
     * 查询入库单
     */
    public StockInOrder selectStockInOrderByOrderNo(String orderNo);

    /**
     * 查询入库单列表
     *
     * @param stockInOrder 入库单
     * @return 入库单集合
     */
    public List<StockInOrder> selectStockInOrderList(StockInOrder stockInOrder);

    /**
     * 新增入库单
     *
     * @param stockInOrder 入库单
     * @return 结果
     */
    public int insertStockInOrder(StockInOrder stockInOrder);

    /**
     * 修改入库单
     *
     * @param stockInOrder 入库单
     * @return 结果
     */
    public int updateStockInOrder(StockInOrder stockInOrder);

    /**
     * 修改入库单
     */
    public int updateStockInOrderByOrderNo(StockInOrder stockInOrder);

    /**
     * 删除入库单
     *
     * @param orderId 入库单主键
     * @return 结果
     */
    public int deleteStockInOrderByOrderId(Long orderId);

    /**
     * 批量删除入库单
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockInOrderByOrderIds(Long[] orderIds);
}
