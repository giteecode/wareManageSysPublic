package com.ruoyi.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.domain.StockInDetail;
import com.ruoyi.stock.domain.StockInOrder;
import com.ruoyi.stock.domain.stats.StockInStats;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 入库单Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockInOrderService {

    /**
     * 查询入库单数量
     */
    public Map<String, Long> selectStockInOrderTotal(Date selectDate);

    /**
     * 查询入库单
     *
     * @param orderId 入库单主键
     * @return 入库单
     */
    public StockInOrder selectStockInOrderByOrderId(Long orderId);

    /**
     * 查询入库单
     *
     * @param orderNo
     * @return 入库单
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
     */
    public int insertStockInOrder(String username, StockInOrder stockInOrder);

    /**
     * 修改入库单
     *
     * @param stockInOrder 入库单
     * @return 结果
     */
    public int updateStockInOrder(StockInOrder stockInOrder);

    /**
     * 批量删除入库单
     *
     * @param orderIds 需要删除的入库单主键集合
     * @return 结果
     */
    public int deleteStockInOrderByOrderIds(Long[] orderIds);

    /**
     * 删除入库单信息
     *
     * @param orderId 入库单主键
     * @return 结果
     */
    public int deleteStockInOrderByOrderId(Long orderId);

    /**
     * 入库单质检
     */
    public int inOrderCheck(String username, List<StockInDetail> detailList);

    /**
     * 扫码提交入库单-入库
     */
    public AjaxResult submitStockIn(String username, StockInOrder stockInOrder);

    /**
     * 入库统计
     */
    public List<StockInStats> selectStockInStatsList(String matCode, String matName);

}
