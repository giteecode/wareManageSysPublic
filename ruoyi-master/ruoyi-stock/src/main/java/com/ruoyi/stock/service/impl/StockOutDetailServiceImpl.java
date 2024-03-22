package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockOutDetailMapper;
import com.ruoyi.stock.domain.StockOutDetail;
import com.ruoyi.stock.service.IStockOutDetailService;

/**
 * 出库单详情Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-25
 */
@Service
public class StockOutDetailServiceImpl implements IStockOutDetailService {
    @Autowired
    private StockOutDetailMapper stockOutDetailMapper;

    /**
     * 查询出库单详情
     *
     * @param detailId 出库单详情主键
     * @return 出库单详情
     */
    @Override
    public StockOutDetail selectStockOutDetailByDetailId(Long detailId) {
        return stockOutDetailMapper.selectStockOutDetailByDetailId(detailId);
    }

    /**
     * 查询出库单详情列表
     *
     * @param stockOutDetail 出库单详情
     * @return 出库单详情
     */
    @Override
    public List<StockOutDetail> selectStockOutDetailList(StockOutDetail stockOutDetail) {
        return stockOutDetailMapper.selectStockOutDetailList(stockOutDetail);
    }

    /**
     * 查询出库单列表
     */
    public List<StockOutDetail> selectStockOutDetailListByOrderNo(String orderNo){
        return  stockOutDetailMapper.selectStockOutDetailListByOrderNo(orderNo);
    }

    /**
     * 新增出库单详情
     *
     * @param stockOutDetail 出库单详情
     * @return 结果
     */
    @Override
    public int insertStockOutDetail(StockOutDetail stockOutDetail) {
        stockOutDetail.setCreateTime(DateUtils.getNowDate());
        return stockOutDetailMapper.insertStockOutDetail(stockOutDetail);
    }

    /**
     * 修改出库单详情
     *
     * @param stockOutDetail 出库单详情
     * @return 结果
     */
    @Override
    public int updateStockOutDetail(StockOutDetail stockOutDetail) {
        stockOutDetail.setUpdateTime(DateUtils.getNowDate());
        return stockOutDetailMapper.updateStockOutDetail(stockOutDetail);
    }

    /**
     * 批量删除出库单详情
     *
     * @param detailIds 需要删除的出库单详情主键
     * @return 结果
     */
    @Override
    public int deleteStockOutDetailByDetailIds(Long[] detailIds) {
        return stockOutDetailMapper.deleteStockOutDetailByDetailIds(detailIds);
    }

    /**
     * 删除出库单详情信息
     *
     * @param detailId 出库单详情主键
     * @return 结果
     */
    @Override
    public int deleteStockOutDetailByDetailId(Long detailId) {
        return stockOutDetailMapper.deleteStockOutDetailByDetailId(detailId);
    }
}
