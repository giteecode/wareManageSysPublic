package com.ruoyi.stock.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.stock.domain.stats.StockRecordStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockRecordMapper;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.service.IStockRecordService;

/**
 * 库存流水Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockRecordServiceImpl implements IStockRecordService {
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询库存流水
     *
     * @param recordId 库存流水主键
     * @return 库存流水
     */
    @Override
    public StockRecord selectStockRecordByRecordId(Long recordId) {
        return stockRecordMapper.selectStockRecordByRecordId(recordId);
    }

    /**
     * 查询库存流水列表
     *
     * @param stockRecord 库存流水
     * @return 库存流水
     */
    @Override
    public List<StockRecord> selectStockRecordList(StockRecord stockRecord) {
        return stockRecordMapper.selectStockRecordList(stockRecord);
    }

    /**
     * 查询库存流水列表（退料）
     */
    @Override
    public List<StockRecord> selectStockRecordListByOrderNo(String orderNo){
        return stockRecordMapper.selectStockRecordListByOrderNo(orderNo);
    }

    /**
     * 新增库存流水
     *
     * @param stockRecord 库存流水
     * @return 结果
     */
    @Override
    public int insertStockRecord(StockRecord stockRecord) {
        stockRecord.setCreateTime(DateUtils.getNowDate());
        return stockRecordMapper.insertStockRecord(stockRecord);
    }

    /**
     * 修改库存流水
     *
     * @param stockRecord 库存流水
     * @return 结果
     */
    @Override
    public int updateStockRecord(StockRecord stockRecord) {
        stockRecord.setUpdateTime(DateUtils.getNowDate());
        return stockRecordMapper.updateStockRecord(stockRecord);
    }

    /**
     * 批量删除库存流水
     *
     * @param recordIds 需要删除的库存流水主键
     * @return 结果
     */
    @Override
    public int deleteStockRecordByRecordIds(Long[] recordIds) {
        return stockRecordMapper.deleteStockRecordByRecordIds(recordIds);
    }

    /**
     * 删除库存流水信息
     *
     * @param recordId 库存流水主键
     * @return 结果
     */
    @Override
    public int deleteStockRecordByRecordId(Long recordId) {
        return stockRecordMapper.deleteStockRecordByRecordId(recordId);
    }

    /**
     * 统计库存操作
     */
    @Override
    public List<StockRecordStats> statsStockRecord(StockRecordStats recordStats){
        return stockRecordMapper.statsStockRecord(recordStats);
    }

}
