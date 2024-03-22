package com.ruoyi.stock.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockAllotDetailMapper;
import com.ruoyi.stock.domain.StockAllotDetail;
import com.ruoyi.stock.service.IStockAllotDetailService;

/**
 * 调拨单详情Service业务层处理
 *
 * @author ruoyi
 * @date 2022-08-05
 */
@Service
public class StockAllotDetailServiceImpl implements IStockAllotDetailService {
    @Autowired
    private StockAllotDetailMapper stockAllotDetailMapper;

    /**
     * 查询调拨单详情
     *
     * @param detailId 调拨单详情主键
     * @return 调拨单详情
     */
    @Override
    public StockAllotDetail selectStockAllotDetailByDetailId(Long detailId) {
        return stockAllotDetailMapper.selectStockAllotDetailByDetailId(detailId);
    }

    /**
     * 查询调拨单详情列表
     *
     * @param stockAllotDetail 调拨单详情
     * @return 调拨单详情
     */
    @Override
    public List<StockAllotDetail> selectStockAllotDetailList(StockAllotDetail stockAllotDetail) {
        return stockAllotDetailMapper.selectStockAllotDetailList(stockAllotDetail);
    }

    /**
     * 查询调拨单详情列表
     */
    @Override
    public List<StockAllotDetail> selectStockAllotDetailListByAllotNo(String allotNo){
        return stockAllotDetailMapper.selectStockAllotDetailListByAllotNo(allotNo);
    }

    /**
     * 新增调拨单详情
     *
     * @param stockAllotDetail 调拨单详情
     * @return 结果
     */
    @Override
    public int insertStockAllotDetail(StockAllotDetail stockAllotDetail) {
        stockAllotDetail.setCreateTime(DateUtils.getNowDate());
        return stockAllotDetailMapper.insertStockAllotDetail(stockAllotDetail);
    }

    /**
     * 修改调拨单详情
     *
     * @param stockAllotDetail 调拨单详情
     * @return 结果
     */
    @Override
    public int updateStockAllotDetail(StockAllotDetail stockAllotDetail) {
        stockAllotDetail.setUpdateTime(DateUtils.getNowDate());
        return stockAllotDetailMapper.updateStockAllotDetail(stockAllotDetail);
    }

    /**
     * 批量删除调拨单详情
     *
     * @param detailIds 需要删除的调拨单详情主键
     * @return 结果
     */
    @Override
    public int deleteStockAllotDetailByDetailIds(Long[] detailIds) {
        return stockAllotDetailMapper.deleteStockAllotDetailByDetailIds(detailIds);
    }

    /**
     * 删除调拨单详情信息
     *
     * @param detailId 调拨单详情主键
     * @return 结果
     */
    @Override
    public int deleteStockAllotDetailByDetailId(Long detailId) {
        return stockAllotDetailMapper.deleteStockAllotDetailByDetailId(detailId);
    }
}
