package com.ruoyi.stock.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.domain.stats.StockRecordStats;
import org.apache.ibatis.annotations.Param;

/**
 * 库存流水Service接口
 *
 * @author ruoyi
 * @date 2022-07-25
 */
public interface IStockRecordService {
    /**
     * 查询库存流水
     *
     * @param recordId 库存流水主键
     * @return 库存流水
     */
    public StockRecord selectStockRecordByRecordId(Long recordId);

    /**
     * 查询库存流水列表
     *
     * @param stockRecord 库存流水
     * @return 库存流水集合
     */
    public List<StockRecord> selectStockRecordList(StockRecord stockRecord);

    /**
     * 查询库存流水列表（退料）
     */
    public List<StockRecord> selectStockRecordListByOrderNo(String orderNo);

    /**
     * 新增库存流水
     *
     * @param stockRecord 库存流水
     * @return 结果
     */
    public int insertStockRecord(StockRecord stockRecord);

    /**
     * 修改库存流水
     *
     * @param stockRecord 库存流水
     * @return 结果
     */
    public int updateStockRecord(StockRecord stockRecord);

    /**
     * 批量删除库存流水
     *
     * @param recordIds 需要删除的库存流水主键集合
     * @return 结果
     */
    public int deleteStockRecordByRecordIds(Long[] recordIds);

    /**
     * 删除库存流水信息
     *
     * @param recordId 库存流水主键
     * @return 结果
     */
    public int deleteStockRecordByRecordId(Long recordId);

    /**
     * 统计库存操作
     */
    public List<StockRecordStats> statsStockRecord(StockRecordStats recordStats);

}
